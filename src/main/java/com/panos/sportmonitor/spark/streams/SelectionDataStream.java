package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.dto.Bet;
import com.panos.sportmonitor.spark.dto.BetSelection;
import com.panos.sportmonitor.spark.dto.SelectionData;
import org.apache.commons.math3.util.Precision;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

import java.util.*;

import static org.apache.spark.sql.functions.*;

public class SelectionDataStream extends AbstractJavaStream<SelectionData> {
    public SelectionDataStream(JavaDStream<SelectionData> stream) {
        super(stream);
    }

    public void appendToSelectionDataTable(Dataset<Row> activeSelections) {
        Dataset<Row> ds = activeSelections
                .filter(col("currentPrice").notEqual(col("prevPrice")));
        PostgresHelper.appendDataset(ds, "selection_data");
    }

    public void overwriteActiveSelections(Dataset<Row> activeSelections) {
        if (!activeSelections.isEmpty()) {
            Dataset<Row> ds = activeSelections
                    .select("selectionId", "timestamp", "currentPrice");
            PostgresHelper.overwriteDataset(ds, "active_selections");
        }
    }

    public void generateBets(List<SelectionData> activeSelections, int count) {
        List<Bet> bets = new ArrayList<>();
        List<BetSelection> betSelections = new ArrayList<>();
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < count; i++) {
            Tuple2<Bet, List<BetSelection>> t = createBetSlip(activeSelections, i % 2 == 0 ? 1 : random.nextInt(5) + 1);
            if (t._2.size() > 0) {
                bets.add(t._1);
                betSelections.addAll(t._2);
            }
        }

        Dataset<Row> betsDs = SparkSession.active().createDataFrame(bets, Bet.class);
        PostgresHelper.appendDataset(betsDs, "bets");

        Dataset<Row> betSelectionsDs = SparkSession.active().createDataFrame(betSelections, BetSelection.class);
        PostgresHelper.appendDataset(betSelectionsDs, "bet_selections");
    }

    private Tuple2<Bet, List<BetSelection>> createBetSlip(List<SelectionData> activeSelections, int numBets) {
        String betId = UUID.randomUUID().toString();

        List<BetSelection> selections = new ArrayList<>();
        int actualSelections = Math.min(numBets, activeSelections.size());
        Iterator<Integer> it= getRandomIndexes(actualSelections, activeSelections.size());
        float totalOdd = 1;
        long betStamp = Long.MAX_VALUE;
        while (it.hasNext()) {
            SelectionData s = activeSelections.get(it.next());
            long selectionId = s.getSelectionId();
            long timestamp = s.getTimestamp();
            double currentPrice = s.getCurrentPrice();
            betStamp = Long.min(betStamp, timestamp);
            selections.add(new BetSelection(betId, selectionId, currentPrice));
            totalOdd *= currentPrice;
        }

        float cashOutRatio =  0.75F;
        Bet bet = new Bet(betId,
                betStamp,
                actualSelections,
                Precision.round(totalOdd, 2),
                Precision.round(totalOdd * cashOutRatio, 2)
        );
        return new Tuple2<>(bet, selections);
    }

    private Iterator<Integer> getRandomIndexes(int actualSelections, int bound) {
        Random random = new Random(new Date().getTime());
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < actualSelections) {
            set.add(random.nextInt(bound));
        }
        return set.iterator();
    }

}
