package com.panos.sportmonitor.spark.pipelines.cashout;

import com.panos.sportmonitor.spark.pipelines.sessions.sources.KafkaOverviewSource;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Serializable;
import scala.Tuple2;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.round;

import java.util.*;

@Service
public class CashOutPipeline implements Serializable {
    @Autowired
    private KafkaOverviewSource kafkaOverviewSource;

    public void run(SparkSession spark, JavaStreamingContext streamingContext) {

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
                });

        currentSelectionsStream
                .filter(e -> !e.isEventSuspended() && !e.getIsMarketSuspended())
                .foreachRDD((rdd, time) -> {
                    BetGenerator.createBetSlips(spark, rdd, 2); //rdd.count());
                });

        currentSelectionsStream
                .foreachRDD((rdd, time) -> {
                    Dataset<Row> currentSelections = spark.createDataFrame(rdd, SelectionEvent.class);
                    currentSelections.createOrReplaceTempView("currentSelections");
                });

        JavaDStream<SelectionEvent> updatedSelectionsStream = currentSelectionsStream
                .mapToPair(e -> new Tuple2<>(e.getSelectionId(), e))
                .mapWithState(StateSpec.function(CashOutPipeline::selectionEventMapping).timeout(Durations.minutes(120)))
                .filter(Objects::nonNull);
        updatedSelectionsStream.foreachRDD((rdd, time) -> {
            Dataset<Row> updatedSelections = spark.createDataFrame(rdd, SelectionEvent.class)
                    .select("selectionId", "price")
                    .withColumnRenamed("price", "newPrice")
                    .as("a")
                    ;
            Dataset<Row> affectedBets = updatedSelections
                    .join(spark.table("betSelections"), "selectionId")
                    .select("betId")
                    .distinct()
                    ;
            Dataset<Row> affectedCurrentOdds = affectedBets
                    .join(spark.table("betSelections"), "betId")
                    .as("bs")
                    .joinWith(updatedSelections,
                            col("bs.selectionId").equalTo(col("a.selectionId")),
                            "left_outer"
                            )
                    .withColumn("currentPrice", functions.coalesce(
                            col("_2.newPrice"),
                            col("_1.price")))
                    .select("_1.betId", "currentPrice")
                    .groupBy("betId")
                    .agg(functions.round(functions.exp(functions.sum(functions.log(col("currentPrice")))), 2).as("currentOdd"))
                    ;

            Dataset<Row> df3 = spark.table("bets")
                    .join(affectedCurrentOdds, "betId")
                    .select("betId", "selections", "amount", "totalReturn", "cashOut", "totalOdd", "currentOdd", "cashOutOdd")
                    .withColumn("diff", round(col("currentOdd").minus(col("cashOutOdd")), 2))
                    .withColumn("autoCashOut", col("diff").leq(0))
                    ;

            //System.out.println(String.format("ds contains %d rows", df3.count()));
            df3.show();
        });

        System.out.println("CashOutPipeline is running");
    }

    private static SelectionEvent selectionEventMapping(long selectionId, Optional<SelectionEvent> newSelection, State<Float> state) {
        SelectionEvent result = null;
        if (!state.isTimingOut() && newSelection.isPresent()) {
            SelectionEvent newEvent = newSelection.get();
            float newPrice = newEvent.getPrice();
            if (state.exists()) {
                float prevPrice = state.get();
                if (prevPrice != newPrice) {
                    result = newEvent;
                    state.update(newPrice);
                }
            }
            else {
                result = newEvent;
                state.update(newPrice);
            }
        }
        return result;
    }
}
