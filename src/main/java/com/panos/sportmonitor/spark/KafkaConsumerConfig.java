package com.panos.sportmonitor.spark;

import com.panos.sportmonitor.spark.pipelines.overview.PipelineOverviewKafkaParams;
import com.panos.sportmonitor.spark.pipelines.radar.PipelineRadarKafkaParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${spark.overview.kafka.group-id}")
    private String overviewGroupId;
    @Value(value = "${spark.overview.kafka.auto-offset-reset}")
    private String overviewAutoOffsetReset;
    @Value(value = "${spark.overview.kafka.key-deserializer}")
    private String overviewKeyDeserializer;
    @Value(value = "${spark.overview.kafka.value-deserializer}")
    private String overviewValueDeserializer;

    @Value(value = "${spark.radar.kafka.group-id}")
    private String radarGroupId;
    @Value(value = "${spark.radar.kafka.auto-offset-reset}")
    private String radarAutoOffsetReset;
    @Value(value = "${spark.radar.kafka.key-deserializer}")
    private String radarKeyDeserializer;
    @Value(value = "${spark.radar.kafka.value-deserializer}")
    private String radarValueDeserializer;

    @Bean
    public PipelineOverviewKafkaParams overviewKafkaConsumerParams() throws ClassNotFoundException {
        PipelineOverviewKafkaParams kafkaParams = new PipelineOverviewKafkaParams();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", Class.forName(overviewKeyDeserializer));
        kafkaParams.put("value.deserializer", Class.forName(overviewValueDeserializer));
        kafkaParams.put("group.id", overviewGroupId);
        kafkaParams.put("auto.offset.reset", overviewAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    @Bean
    public PipelineRadarKafkaParams radarKafkaConsumerParams() throws ClassNotFoundException {
        PipelineRadarKafkaParams kafkaParams = new PipelineRadarKafkaParams();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", Class.forName(radarKeyDeserializer));
        kafkaParams.put("value.deserializer", Class.forName(radarValueDeserializer));
        kafkaParams.put("group.id", radarGroupId);
        kafkaParams.put("auto.offset.reset", radarAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }
}
