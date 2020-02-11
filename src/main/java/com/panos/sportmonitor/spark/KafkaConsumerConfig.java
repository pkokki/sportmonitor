package com.panos.sportmonitor.spark;

import com.panos.sportmonitor.spark.pipelines.overview.EventDeserializer;
import com.panos.sportmonitor.spark.pipelines.overview.PipelineOverviewKafkaParams;
import com.panos.sportmonitor.spark.pipelines.radar.PipelineRadarKafkaParams;
import org.apache.kafka.common.serialization.StringSerializer;
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

    @Value(value = "${spark.radar.kafka.group-id}")
    private String radarGroupId;
    @Value(value = "${spark.radar.kafka.auto-offset-reset}")
    private String radarAutoOffsetReset;

    @Bean
    public PipelineOverviewKafkaParams overviewKafkaConsumerParams() {
        PipelineOverviewKafkaParams kafkaParams = new PipelineOverviewKafkaParams();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", StringSerializer.class);
        kafkaParams.put("value.deserializer", EventDeserializer.class);
        kafkaParams.put("group.id", overviewGroupId);
        kafkaParams.put("auto.offset.reset", overviewAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    @Bean
    public PipelineRadarKafkaParams radarKafkaConsumerParams() {
        PipelineRadarKafkaParams kafkaParams = new PipelineRadarKafkaParams();
        kafkaParams.put("bootstrap.servers", bootstrapAddress);
        kafkaParams.put("key.deserializer", StringSerializer.class);
        kafkaParams.put("value.deserializer", StringSerializer.class);
        kafkaParams.put("group.id", radarGroupId);
        kafkaParams.put("auto.offset.reset", radarAutoOffsetReset);
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }
}
