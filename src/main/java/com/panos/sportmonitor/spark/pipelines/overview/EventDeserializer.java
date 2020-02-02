package com.panos.sportmonitor.spark.pipelines.overview;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.dto.Event;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class EventDeserializer implements Deserializer<Event> {
    private final static Logger logger = LoggerFactory.getLogger(EventDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Event deserialize(String topic, byte[] data) {
        Event event = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            event = mapper.readValue(data, Event.class);
        } catch (IOException e) {
            logger.error("Failed to deserialize object: " + data.toString(), e);
        }
        return event;
    }

    @Override
    public Event deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public void close() {
    }
}
