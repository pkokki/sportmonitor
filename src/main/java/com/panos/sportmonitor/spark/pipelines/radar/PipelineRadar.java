package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.spark.PostgresHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

public class PipelineRadar {
    public static void run(SparkSession spark, JavaStreamingContext streamingContext) {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "overviews_stream");
        kafkaParams.put("auto.offset.reset", "latest"); // earliest, latest, none
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("RADAR");

        // Create radar events stream from kafka
        JavaInputDStream<ConsumerRecord<String, String>> kafkaStream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        JavaDStream<RadarJsonEvent> messageStream = kafkaStream
                .map(ConsumerRecord::value)
                .map(jsonData -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode;
                    try {
                        rootNode = objectMapper.readTree(jsonData);
                    } catch (JsonParseException ex) {
                        return null;
                    }
                    String queryUrl = rootNode.path("queryUrl").asText();
                    Iterator<JsonNode> elements = rootNode.path("doc").elements();
                    if (elements.hasNext()) {
                        JsonNode doc = elements.next();
                        return new RadarJsonEvent(queryUrl, doc.path("event").asText(), doc.path("_dob").asLong(), doc.path("_maxage").asInt(), doc.path("data"));
                    }
                    return null;
                })
                .filter(s -> s != null);

        messageStream
               .mapToPair(r -> new Tuple2<>(r.event, 1))
                .reduceByKey((a, b) -> a + b)
                .print(100);
        processMatchTimelineEvents(spark, messageStream);
        processMatchSituations(spark, messageStream);
    }

    private static void processMatchSituations(SparkSession spark, JavaDStream<RadarJsonEvent> messageStream) {
        JavaDStream<MatchSituationEvent> matchSituationEvents = messageStream
                .filter(r -> r.event.equals("stats_match_situation"))
                .flatMap(r -> {
                    List<MatchSituationEvent> list = new ArrayList<>();
                    long matchid = Long.parseLong(r.data.path("matchid").asText());
                    Iterator<JsonNode> nodes = r.data.path("data").elements();
                    while (nodes.hasNext()) {
                        JsonNode node = nodes.next();
                        list.add(new MatchSituationEvent(matchid, node));
                    }
                    return list.iterator();
                })
                .mapToPair(mse -> new Tuple2<>(mse.getId(), mse))
                .mapWithState(StateSpec.function(PipelineRadar::onlyOneMatchSituationEventSpec))
                .filter(r -> r.isPresent())
                .map(r -> r.get());
        matchSituationEvents.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MatchSituationEvent.class);
                PostgresHelper.appendDataset(ds, "match_situation_events");
            }
        });
    }
    private static void processMatchTimelineEvents(SparkSession spark, JavaDStream<RadarJsonEvent> messageStream) {
        JavaDStream<MatchTimelineEvent> matchTimelineEvents = messageStream
                .filter(r -> r.event.equals("match_timeline"))
                .flatMap(r -> {
                    List<MatchTimelineEvent> list = new ArrayList<>();
                    Iterator<JsonNode> events = r.data.path("events").elements();
                    while (events.hasNext()) {
                        JsonNode evNode = events.next();
                        if (evNode != null)
                            list.add(new MatchTimelineEvent(evNode));
                    }
                    return list.iterator();
                })
                .mapToPair(mte -> new Tuple2<>(mte.getId(), mte))
                .mapWithState(StateSpec.function(PipelineRadar::onlyOneMatchTimelineEventSpec))
                .filter(r -> r.isPresent())
                .map(r -> r.get());

        matchTimelineEvents.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MatchTimelineEvent.class);
                PostgresHelper.appendDataset(ds, "match_timeline_events");
            }
        });
    }

    private static Optional<MatchTimelineEvent> onlyOneMatchTimelineEventSpec(Long id, Optional<MatchTimelineEvent> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    private static Optional<MatchSituationEvent> onlyOneMatchSituationEventSpec(Long id, Optional<MatchSituationEvent> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

}
