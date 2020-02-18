package com.panos.sportmonitor.spark.pipelines.cashout;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.spark.PostgresHelper;
import com.panos.sportmonitor.spark.pipelines.overview.EventMasterData;
import com.panos.sportmonitor.spark.pipelines.overview.EventRecord;
import com.panos.sportmonitor.spark.pipelines.overview.MarketRecord;
import com.panos.sportmonitor.spark.pipelines.sessions.models.RawOverviewEvent;
import com.panos.sportmonitor.spark.pipelines.sessions.sources.KafkaOverviewSource;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
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

import java.util.*;

import static org.apache.spark.sql.functions.*;

@Service
public class CashOutPipeline implements Serializable {
    @Autowired
    private KafkaOverviewSource kafkaOverviewSource;

    public void run(SparkSession spark, JavaStreamingContext streamingContext) {
        JavaDStream<RawOverviewEvent> rawEvents = kafkaOverviewSource
                .run(streamingContext);

        rawEvents
                .flatMap(e -> {
                    List<SelectionEvent> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionEvent(
                                        e.getId(), e.getTimestamp(), e.getIsSuspended(),
                                        m.getId(), m.getIsSuspended(),
                                        s.getId(), s.getDescription(), s.getPrice()
                                )
                        );
                    }));
                    return list.iterator();
                })
                .mapToPair(e -> new Tuple2<>(e.getSelectionId(), e))
                .mapWithState(StateSpec.function(CashOutPipeline::selectionEventMapping).timeout(Durations.minutes(120)))
                .filter(Objects::nonNull)
                .foreachRDD(rdd -> {
                    if (rdd.isEmpty())
                        return;

                    // Overwrite active_selections
                    Dataset<Row> activeSelections = spark.createDataFrame(rdd, SelectionEvent.class);
                    //activeSelections.show();
                    Arrays.stream(activeSelections.columns()).forEach(col -> activeSelections.withColumnRenamed(col, col.toLowerCase()));
                    PostgresHelper.overwriteDataset(activeSelections, "active_selections");
                    System.out.println(String.format("There are %d active selections.", activeSelections.count()));

                    // Generate random bets
                    final int BETS = 100;
                    List<SelectionEvent> odds = rdd
                            .filter(SelectionEvent::getIsActive)
                            .collect();
                    Tuple2<List<Bet>, List<BetSelection>> result = BetGenerator.createBetSlips(odds, BETS);
                    Dataset<Row> bets = spark.createDataFrame(result._1, Bet.class);
                    Arrays.stream(bets.columns()).forEach(c -> bets.withColumnRenamed(c, c.toLowerCase()));
                    PostgresHelper.appendDataset(bets, "bets");

                    Dataset<Row> betSelections = spark.createDataFrame(result._2, BetSelection.class);
                    Arrays.stream(betSelections.columns()).forEach(c -> betSelections.withColumnRenamed(c, c.toLowerCase()));
                    PostgresHelper.appendDataset(betSelections, "bet_selections");

                    System.out.println(String.format("Generated %d random bets.", result._1.size()));
                });

        rawEvents
                .mapToPair(e -> new Tuple2<>(e.getId(), e))
                .mapWithState(StateSpec.function(CashOutPipeline::onlyOneEventSpec))
                .filter(r -> r.isPresent())
                .map(r -> new EventMasterData(r.get()))
                .foreachRDD(rdd -> {
                    Dataset<Row> ds = spark.createDataFrame(rdd, EventMasterData.class);
                    PostgresHelper.appendDataset(ds, "event_master_data");
                });

        // Append to db event_data
        rawEvents
                .map(e -> new EventRecord(e))
                .foreachRDD(rdd -> {
                    if (!rdd.isEmpty()) {
                        Dataset<Row> ds = spark.createDataFrame(rdd, EventRecord.class);
                        PostgresHelper.appendDataset(ds, "event_data");
                    }
                });
        // Append to db market_data
        rawEvents
                .flatMap(e -> {
                    List<MarketRecord> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> list.add(new MarketRecord(e.getId(), e.getTimestamp(), m)));
                    return list.iterator();
                })
                .foreachRDD(rdd -> {
                    if (!rdd.isEmpty()) {
                        Dataset<Row> ds = spark.createDataFrame(rdd, MarketRecord.class);
                        PostgresHelper.appendDataset(ds, "market_data");
                    }
                });

        System.out.println("CashOutPipeline is running");
    }

    public void run_v1(SparkSession spark, JavaStreamingContext streamingContext) {
        LongAccumulator betsAccum = streamingContext.sparkContext().sc().longAccumulator();
        LongAccumulator betSelectionsAccum = streamingContext.sparkContext().sc().longAccumulator();
        Broadcast<List<String>> cashedOutBetsVar = streamingContext.sparkContext().broadcast(new ArrayList<>());

        JavaDStream<SelectionEvent> currentSelectionsStream = kafkaOverviewSource
                .run(streamingContext)
                .flatMap(e -> {
                    List<SelectionEvent> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionEvent(
                                        e.getId(), e.getTimestamp(), e.getIsSuspended(),
                                        m.getId(), m.getIsSuspended(),
                                        s.getId(), s.getDescription(), s.getPrice()
                                )
                        );
                    }));
                    return list.iterator();
                })
                .mapToPair(e -> new Tuple2<>(e.getSelectionId(), e))
                .mapWithState(StateSpec.function(CashOutPipeline::selectionEventMapping).timeout(Durations.minutes(120)))
                .filter(Objects::nonNull);

        currentSelectionsStream.foreachRDD((rdd, time) -> {
            if (rdd.isEmpty())
                return;

            // Generate random bets
            if (!spark.catalog().tableExists("bets")) {
                int BETS = 20000;
                List<SelectionEvent> odds = rdd
                        .filter(s -> s.getIsActive())
                        .collect();
                Tuple2<List<Bet>, List<BetSelection>> result = BetGenerator.createBetSlips(odds, BETS);
                appendOrCreateView(spark, "bets", spark.createDataFrame(result._1, Bet.class), time);
                appendOrCreateView(spark, "betSelections", spark.createDataFrame(result._2, BetSelection.class), time);
                betsAccum.add(result._1.size());
                betSelectionsAccum.add(result._2.size());
            }

            // Cache RDD
            rdd.cache();

            // Changed selections
            Dataset<Row> changedSelectionsDF = spark.createDataFrame(rdd, SelectionEvent.class);

            // All generated bets (except already cached out) and bet selections
            Dataset<Row> betsDF = spark.table("bets")
                    .select("betId", "betStamp", "selections", "initialPrice", "cashOutPrice", "logCashOutPrice")
                    .filter(functions.not(col("betId").isInCollection(cashedOutBetsVar.value())))
                    .join(spark
                            .table("betSelections")
                            .select("betId", "selectionId", "price", "logPrice")
                            , "betId")
                    .cache();
                    ;

            // Query to find bets to cashout
            Dataset<Row> betsToCashOutDF = betsDF
                    .join(functions.broadcast(changedSelectionsDF.filter(col("priceDiff").leq(0))),
                            JavaConversions.asScalaBuffer(Lists.newArrayList("selectionId")), "left_outer")
                    .withColumn("logCurrentPrice", functions.coalesce(col("logCurrentPrice"), col("logPrice")))
                    .groupBy("betId", "logCashOutPrice")
                    .agg(functions.sum(col("logCurrentPrice")).as("logCurrentTotal"))
                    .filter(col("logCurrentTotal").leq(col("logCashOutPrice")))
                    ;

            // Output
            if (betsToCashOutDF.isEmpty()) {
                System.out.println(String.format("No bets found to auto cashout in %s.",
                        new Time(new Date().getTime()).minus(time)));
            } else {
                List<String> betIds = betsToCashOutDF
                        .select("betId")
                        .distinct()
                        .map((MapFunction<Row, String>) r -> r.getString(0), Encoders.STRING())
                        .collectAsList();
                cashedOutBetsVar.value().addAll(betIds);

                System.out.println(String.format("Found %d new cashouts (total=%d out of %d bets with %d selections) in %s.",
                        betsToCashOutDF.count(),
                        cashedOutBetsVar.value().size(),
                        betsAccum.value(),
                        betSelectionsAccum.value(),
                        new Time(new Date().getTime()).minus(time)));
                betsDF
                        .drop("logCashOutPrice", "logPrice")
                        //.select("betId", "betStamp", "selections", "initialPrice", "cashOutPrice", "selectionId", "price")
                        .filter(col("betId").isInCollection(betIds))
                        .join(functions.broadcast(changedSelectionsDF
                                        .select("selectionId", "eventId", "marketId", "timestamp", "isActive", "prevPrice", "currentPrice", "priceDiff")),
                                JavaConversions.asScalaBuffer(Lists.newArrayList("selectionId")), "left_outer")
                        .orderBy("betId")
                        .show();
            }

            // Remove RDD from cache
            rdd.unpersist();
        });
    }

    private static void appendOrCreateView(SparkSession spark, String viewName, Dataset<Row> newData, Time time) {
        if (spark.catalog().tableExists(viewName)) {
            Dataset<Row> existingData = spark.table(viewName);
            Dataset<Row> union = existingData.union(newData);
            union.createOrReplaceTempView(viewName);
            //System.out.println(String.format("TempView %s contains %d rows (%d new)", viewName, union.count(), newData.count()));
        }
        else {
            newData.createOrReplaceTempView(viewName);
            System.out.println(String.format("TempView %s created with %d rows in %s",
                    viewName,
                    newData.count(),
                    new Time(new Date().getTime()).minus(time)));
        }
    }

    private static SelectionEvent selectionEventMapping(long selectionId, Optional<SelectionEvent> newSelection, State<Double> state) {
        if (!state.isTimingOut() && newSelection.isPresent()) {
            SelectionEvent newEvent = newSelection.get();
            double newPrice = newEvent.getCurrentPrice();
            if (state.exists()) {
                double prevPrice = state.get();
                if (prevPrice != newPrice) {
                    state.update(newPrice);
                }
                newEvent.setPrevPrice(prevPrice);
            }
            else {
                state.update(newPrice);
            }
            return newEvent;
        }
        return null;
    }

    private static Optional<RawOverviewEvent> onlyOneEventSpec(String id, Optional<RawOverviewEvent> item, State<String> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }
}
