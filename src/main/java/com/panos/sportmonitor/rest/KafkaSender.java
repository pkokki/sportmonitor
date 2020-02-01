package com.panos.sportmonitor.rest;

import com.panos.sportmonitor.dto.Event;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    private static final KafkaProducer<String, Event> producer = new KafkaProducer<>(
            getProducerConfig("localhost:9092", EventSerializer.class.getName())
    );
    private static final KafkaProducer<String, String> stringProducer = new KafkaProducer<>(
            getProducerConfig("localhost:9092", StringSerializer.class.getName())
    );

    static void send(String topic, String data) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
        logger.info("Sending string record to topic: " + topic);
        stringProducer.send(record, KafkaSender::logResult);
        stringProducer.flush();
    }

    static void send(List<Event> events) {
        events.forEach(event -> {
            ProducerRecord<String, Event> record = new ProducerRecord<>("OVERVIEWS", event);
            logSend(event);
            producer.send(record, KafkaSender::logResult);
        });
        producer.flush();
    }

    private static Properties getProducerConfig(String kafkaHost, String valueSerializer) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "LiveOverviewMonitor");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return props;
    }

    private static void logSend(Event event) {
        logger.info("Sending live overview for event: "
                + event.getTitle() + "," + " at time: "
                + event.getTimestamp());
    }

    private static void logResult(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            logger.error("Error sending data", exception);
        }
        else {
            logger.info("Successfully sent data to topic: "
                    + metadata.topic() + " and partition: "
                    + metadata.partition() + " with offset: "
                    + metadata.offset());
        }
    }
}
