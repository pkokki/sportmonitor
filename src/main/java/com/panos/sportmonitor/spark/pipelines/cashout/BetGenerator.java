package com.panos.sportmonitor.spark.pipelines.cashout;

import org.apache.commons.math3.util.Precision;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.*;

public class BetGenerator {
    private static final Random random = new Random(new Date().getTime());

    public static void createBetSlips(SparkSession spark, JavaRDD<SelectionEvent> rdd, long count) {
        List<SelectionEvent> odds = rdd.collect();
        List<Bet> bets = new ArrayList<>();
        List<BetSelection> betSelections = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Tuple2<Bet, List<BetSelection>> t = createBetSlip(odds, i % 2 == 0 ? 1 : random.nextInt(5) + 1);
            if (t._2.size() > 0) {
                bets.add(t._1);
                betSelections.addAll(t._2);
            }
        }

        appendOrCreateView(spark, "bets", spark.createDataFrame(bets, Bet.class));
        appendOrCreateView(spark, "betSelections", spark.createDataFrame(betSelections, BetSelection.class));
    }

    private static void appendOrCreateView(SparkSession spark, String viewName, Dataset<Row> newData) {
        if (spark.catalog().tableExists(viewName)) {
            Dataset<Row> existingData = spark.table(viewName);
            Dataset<Row> union = existingData.union(newData);
            union.createOrReplaceTempView(viewName);
            //System.out.println(String.format("TempView %s contains %d rows (%d new)", viewName, union.count(), newData.count()));
        }
        else {
            newData.createOrReplaceTempView(viewName);
            System.out.println(String.format("TempView %s created with %d rows", viewName, newData.count()));
        }
    }

    private static Tuple2<Bet, List<BetSelection>> createBetSlip(List<SelectionEvent> odds, int numBets) {
        String betId = UUID.randomUUID().toString();

        List<BetSelection> selections = new ArrayList<>();
        int actualSelections = Math.min(numBets, odds.size());
        Iterator<Integer> it= getRandomIndexes(actualSelections, odds.size());
        float totalOdd = 1;
        while (it.hasNext()) {
            SelectionEvent se = odds.get(it.next());
            selections.add(new BetSelection(betId, se.getSelectionId(), se.getPrice()));
            totalOdd *= se.getPrice();
        }

        float cashOutRatio =  0.75F;
        Bet bet = new Bet();
        bet.setBetId(betId);
        bet.setAmount(random.nextInt(5) + 5);
        bet.setSelections(actualSelections);
        bet.setTotalOdd(Precision.round(totalOdd, 2));
        bet.setTotalReturn(Precision.round(totalOdd * bet.getAmount(), 2));
        bet.setCashOut(Precision.round(bet.getTotalReturn() * cashOutRatio, 2));
        bet.setCashOutOdd(Precision.round(totalOdd * cashOutRatio, 2));
        return new Tuple2<>(bet, selections);
    }

    private static Iterator<Integer> getRandomIndexes(int actualSelections, int bound) {
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < actualSelections) {
            set.add(random.nextInt(bound));
        }
        return set.iterator();
    }
}
