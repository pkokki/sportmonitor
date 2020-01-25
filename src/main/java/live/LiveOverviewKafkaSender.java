package live;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

class LiveOverviewKafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(LiveOverviewKafkaSender.class);
    private static final Properties props = getProducerConfig("localhost:9092");
    private static final KafkaProducer<String, LiveOverview.Event> producer = new KafkaProducer<>(props);

    public static void send(List<LiveOverview.Event> events) {
        events.forEach(event -> {
            ProducerRecord<String, LiveOverview.Event> record = new ProducerRecord<>("OVERVIEWS", event);
            logSend(event);
            producer.send(record, LiveOverviewKafkaSender::logResult);
        });
        producer.flush();
    }

    private static Properties getProducerConfig(String kafkaHost) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "LiveOverviewMonitor");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LiveOverviewEventSerializer.class.getName());
        return props;
    }

    private static void logSend(LiveOverview.Event event) {
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
