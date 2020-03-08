package com.panos.sportmonitor.webapi.kafka;

import com.panos.sportmonitor.webapi.Event;
import com.panos.sportmonitor.webapi.EventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.overview.clientId}")
    private String overviewClientId;

    @Bean
    public ProducerFactory<String, Event> overviewProducerFactory() {
        Map<String, Object>  configProps = createCommonConfigProps(overviewClientId, EventSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Event> overviewKafkaTemplate() {
        return new KafkaTemplate<>(overviewProducerFactory());
    }

    @Value(value = "${kafka.radar.clientId}")
    private String radarClientId;

    @Bean
    public ProducerFactory<String, String> radarProducerFactory() {
        Map<String, Object>  configProps = createCommonConfigProps(radarClientId, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> radarKafkaTemplate() {
        return new KafkaTemplate<>(radarProducerFactory());
    }

    private <T> Map<String, Object> createCommonConfigProps(String clientId, Class<T> valueSerializerClass) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, String.valueOf(10 * 1024 * 1024));
        return configProps;
    }

}
