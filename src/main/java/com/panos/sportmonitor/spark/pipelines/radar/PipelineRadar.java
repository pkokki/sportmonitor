package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.spark.PostgresHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.*;

@Service
public class PipelineRadar {
    @Value(value = "${spark.radar.kafka.topic}")
    private String topic;
    @Value("${spark.radar.matchTimelineEvents}")
    private Boolean matchTimelineEvents;
    @Value("${spark.radar.matchSituations}")
    private Boolean matchSituations;
    @Value("${spark.radar.matchDetails}")
    private Boolean matchDetails;

    @Autowired
    private PipelineRadarKafkaParams kafkaParams;
    @Autowired
    private MatchDetailsProcessor matchDetailsProcessor;
    @Autowired
    private MatchTimelineProcessor matchTimelineProcessor;
    @Autowired
    private MatchSituationsProcessor matchSituationsProcessor;

    public void run(SparkSession spark, JavaStreamingContext streamingContext) {
        Collection<String> topics = Collections.singletonList(topic);

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
        System.out.println("PipelineRadar is running");

        if (matchTimelineEvents) {
            matchTimelineProcessor.run(spark, messageStream);
            System.out.println("MatchTimelineProcessor is running");
        }
        if (matchSituations) {
            matchSituationsProcessor.run(spark, messageStream);
            System.out.println("MatchSituationsProcessor is running");
        }
        if (matchDetails) {
            matchDetailsProcessor.run(spark, streamingContext, messageStream);
            System.out.println("MatchDetailsProcessor is running");
        }
    }
}
