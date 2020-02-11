package com.panos.sportmonitor.spark.pipelines.sessions.sources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.spark.pipelines.sessions.models.RawRadarEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class KafkaRadarSource {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${spark.radar.kafka.topic}")
    private String topic;
    @Value(value = "${spark.radar.kafka.group-id}")
    private String radarGroupId;
    @Value(value = "${spark.radar.kafka.auto-offset-reset}")
    private String radarAutoOffsetReset;

    private HashMap<String, Object> kafkaConsumerParams() {
        HashMap<String, Object> kafkaParams = new HashMap<String, Object>();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", radarGroupId);
        kafkaParams.put("auto.offset.reset", radarAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    public JavaDStream<RawRadarEvent> run(JavaStreamingContext streamingContext) {
        Collection<String> topics = Collections.singletonList(topic);

        // Create radar events stream from kafka
        JavaInputDStream<ConsumerRecord<String, String>> kafkaStream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaConsumerParams())
                );

        JavaDStream<RawRadarEvent> radarStream = kafkaStream
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
                        return new RawRadarEvent(queryUrl, doc.path("event").asText(), doc.path("_dob").asLong(), doc.path("_maxage").asInt(), doc.path("data"));
                    }
                    return null;
                })
                .filter(s -> s != null);

        return radarStream;
    }
}
