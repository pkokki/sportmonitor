package com.panos.sportmonitor.spark.pipelines.cashout;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.spark.pipelines.sessions.sources.KafkaOverviewSource;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.util.LongAccumulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Serializable;
import scala.Tuple2;
import scala.collection.JavaConversions;

import static org.apache.spark.sql.functions.col;

import java.time.Duration;
import java.util.*;

@Service
public class CashOutPipeline implements Serializable {
    @Autowired
    private KafkaOverviewSource kafkaOverviewSource;

    public void run(SparkSession spark, JavaStreamingContext streamingContext) {
        LongAccumulator betsAccum = streamingContext.sparkContext().sc().longAccumulator();
        LongAccumulator betSelectionsAccum = streamingContext.sparkContext().sc().longAccumulator();

        JavaDStream<SelectionEvent> currentSelectionsStream = kafkaOverviewSource
                .run(streamingContext)
                .flatMap(e -> {
                    List<SelectionEvent> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionEvent(
                                e.getId(), e.getTimestamp(), e.getIsSuspended(),
                                m.getId(), m.getIsSuspended(),
                                s.getId(), s.getPrice()
                                )
                        );
                    }));
                    return list.iterator();
                })
                .mapToPair(e -> new Tuple2<>(e.getSelectionId(), e))
                .mapWithState(StateSpec.function(CashOutPipeline::selectionEventMapping).timeout(Durations.minutes(120)))
                .filter(Objects::nonNull);

        currentSelectionsStream.foreachRDD((rdd, time) -> {
            rdd.cache();

            // Generate random bets
            List<SelectionEvent> odds = rdd
                    .filter(e -> !e.getIsEventSuspended() && !e.getIsMarketSuspended())
                    .collect();
            Tuple2<List<Bet>, List<BetSelection>> result = BetGenerator.createBetSlips(odds, 2); //rdd.count());
            appendOrCreateView(spark, "bets", spark.createDataFrame(result._1, Bet.class));
            appendOrCreateView(spark, "betSelections", spark.createDataFrame(result._2, BetSelection.class));
            int newBets = result._1.size();
            betsAccum.add(newBets);
            betSelectionsAccum.add(result._2.size());
            //System.out.println(String.format("Generate %d random bets (totals: %d %d): %s",
            //        newBets,
            //        betsAccum.value(),
            //        betSelectionsAccum.value(),
            //        new Time(new Date().getTime()).minus(time)));

            // Current selections
            Dataset<Row> currentSelectionsDF = spark.createDataFrame(rdd, SelectionEvent.class);
            // Changed selections
            Dataset<Row> changedSelectionsDF = currentSelectionsDF.filter(col("priceDiff").notEqual(0F));
            // All generated bets
            Dataset<Row> betsDF = spark.table("bets");
            // All generated bet selections
            Dataset<Row> betSelectionsDF = spark.table("betSelections");
            // Bets with changes
            Dataset<Row> betsWithChangesDF = changedSelectionsDF
                    .join(betSelectionsDF, "selectionId")
                    .select("betId")
                    .distinct()
                    .join(betsDF, "betId")
                    .join(betSelectionsDF, "betId")
                    .join(currentSelectionsDF, "selectionId")
                    ;
            //System.out.println(String.format("Bets with changes: %s", new Time(new Date().getTime()).minus(time)));

            // Find bets to auto cashout
            Dataset<Row> betsToCashOutDF = betsWithChangesDF
                    .select("betId", "currentPrice")
                    .groupBy("betId")
                    .agg(functions.exp(functions.sum(functions.log(col("currentPrice")))).as("currentOdd"))
                    .join(betsDF, "betId")
                    .withColumn("autoCashOut", col("currentOdd").minus(col("cashOutOdd")).leq(0))
                    .filter(col("autoCashOut").equalTo(true))
                    .join(betSelectionsDF, "betId")
                    .join(currentSelectionsDF, "selectionId")
                    .orderBy("betId")
                    ;
            betsToCashOutDF.show();
            System.out.println(String.format("Find bets (total=%d, selections=%d) to auto cashout: %s",
                    betsAccum.value(),
                    betSelectionsAccum.value(),
                    new Time(new Date().getTime()).minus(time)));

            rdd.unpersist();
        });

        System.out.println("CashOutPipeline is running");
    }

    private static void mergeView(SparkSession spark, String viewName, Dataset<Row> newData, String usingColumn) {
        if (spark.catalog().tableExists(viewName)) {
            spark.table(viewName)
                    .join(newData, JavaConversions.asScalaBuffer(Lists.newArrayList(usingColumn)), "left_anti")
                    .union(newData)
                    .createOrReplaceTempView(viewName);
        }
        else {
            newData.createOrReplaceTempView(viewName);
            System.out.println(String.format("TempView %s created with %d rows", viewName, newData.count()));
        }
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

    private static SelectionEvent selectionEventMapping(long selectionId, Optional<SelectionEvent> newSelection, State<Float> state) {
        if (!state.isTimingOut() && newSelection.isPresent()) {
            SelectionEvent newEvent = newSelection.get();
            float newPrice = newEvent.getCurrentPrice();
            if (state.exists()) {
                float prevPrice = state.get();
                if (prevPrice != newPrice) {
                    state.update(newPrice);
                    newEvent.setPrevPrice(prevPrice);
                }
            }
            else {
                state.update(newPrice);
            }
            return newEvent;
        }
        return null;
    }
}
