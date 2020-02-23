package com.panos.sportmonitor.spark.sources;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.spark.dto.RawOverviewEvent;
import com.panos.sportmonitor.spark.streams.RawOverviewEventStream;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaOverviewSource {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${spark.overview.kafka.group-id}")
    private String overviewGroupId;
    @Value(value = "${spark.overview.kafka.auto-offset-reset}")
    private String overviewAutoOffsetReset;
    @Value(value = "${spark.overview.kafka.topic}")
    private String topic;

    private HashMap<String, Object> kafkaConsumerParams() {
        HashMap<String, Object> kafkaParams = new HashMap<String, Object>();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", RawOverviewEventDeserializer.class);
        kafkaParams.put("group.id", overviewGroupId);
        kafkaParams.put("auto.offset.reset", overviewAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    public RawOverviewEventStream createRawOverviewEventStream(JavaStreamingContext streamingContext) {
        Collection<String> topics = Collections.singletonList(topic);

        // Create live events stream from kafka
        JavaInputDStream<ConsumerRecord<String, RawOverviewEvent>> eventRecordDS =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaConsumerParams())
                );
        JavaDStream<RawOverviewEvent> eventsDS = eventRecordDS.map(r -> r.value());
        return new RawOverviewEventStream(eventsDS);
    }

    public static class RawOverviewEventDeserializer implements Deserializer<RawOverviewEvent> {
        private final static Logger logger = LoggerFactory.getLogger(RawOverviewEventDeserializer.class);

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        @Override
        public RawOverviewEvent deserialize(String topic, byte[] data) {
            RawOverviewEvent event = null;

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                event = mapper.readValue(data, RawOverviewEvent.class);
            } catch (IOException e) {
                logger.error("Failed to deserialize object: " + data.toString(), e);
            }
            return event;
        }

        @Override
        public RawOverviewEvent deserialize(String topic, Headers headers, byte[] data) {
            return deserialize(topic, data);
        }

        @Override
        public void close() {
        }
    }
}
