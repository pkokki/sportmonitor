package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.dto.*;
import com.panos.sportmonitor.spark.PostgresHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

public class PipelineOverview {
    public static void run(SparkSession spark, JavaStreamingContext streamingContext) {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", EventDeserializer.class);
        kafkaParams.put("group.id", "overviews_stream");
        kafkaParams.put("auto.offset.reset", "earliest"); // earliest, latest, none
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("OVERVIEWS");

        // Create live events stream from kafka
        JavaInputDStream<ConsumerRecord<String, Event>> eventRecordDS =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        JavaDStream<Event> eventsDS = eventRecordDS.map(r -> r.value());

        eventsDS
                .mapToPair(e -> new Tuple2<>(e.getId(), e))
                .mapWithState(StateSpec.function(PipelineOverview::onlyOneEventSpec))
                .filter(r -> r.isPresent())
                .map(r -> new EventMasterData(r.get()))
                .foreachRDD(rdd -> {
                    Dataset<Row> ds = spark.createDataFrame(rdd, EventMasterData.class);
                    PostgresHelper.appendDataset(ds, "event_master_data");
                });
        if (true)
            return;

        // Append to db event_data
        eventsDS
                .map(e -> new EventRecord(e))
                .foreachRDD(rdd -> {
                    if (!rdd.isEmpty()) {
                        Dataset<Row> ds = spark.createDataFrame(rdd, EventRecord.class);
                        PostgresHelper.appendDataset(ds, "event_data");
                    }
                });

        // Append to db market_data
        JavaDStream<MarketRecord> marketRecords = eventsDS
                .flatMap(e -> {
                    List<MarketRecord> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> list.add(new MarketRecord(e.getId(), e.getTimestamp(), m)));
                    return list.iterator();
                });
        marketRecords.print();

        marketRecords.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MarketRecord.class);
                PostgresHelper.appendDataset(ds, "market_data");
            }
        });

        // Append to db selection_data
        JavaDStream<SelectionRecord> selectionRecords = eventsDS
                .flatMap(e -> {
                    List<SelectionRecord> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionRecord(e.getId(), e.getTimestamp(), m.getId(), s));
                    }));
                    return list.iterator();
                });
        selectionRecords.print();
        selectionRecords.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, SelectionRecord.class);
                PostgresHelper.appendDataset(ds, "selection_data");
            }
        });

        // Apply the state update function to the events streaming Dataset grouped by eventId
        JavaMapWithStateDStream<Long, Event, EventState, LiveEvent> eventUpdates = eventsDS
                .mapToPair(e -> new Tuple2<>(Long.parseLong(e.getId()), e))
                .mapWithState(StateSpec.function(StateFunctions.MappingFunc).timeout(Durations.minutes(1)));
        eventUpdates.print();

        // Overwrite db table event_master_data
        eventUpdates.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, LiveEvent.class);
                PostgresHelper.overwriteDataset(ds, "event_master_data");
            }
        });
    }

    private static Optional<Event> onlyOneEventSpec(String id, Optional<Event> item, State<String> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }
}
