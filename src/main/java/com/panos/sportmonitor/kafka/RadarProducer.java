package com.panos.sportmonitor.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class RadarProducer {
    private static final Logger logger = LoggerFactory.getLogger(RadarProducer.class);

    @Value(value = "${kafka.radar.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        if (message != null && message.length() > 0) {
            logger.info(String.format("%s -> Producing message -> %s", topic, message.substring(0, 100)));
            ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, message);

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info(String.format("%s -> Sent message=[%s] with offset=[%d]", topic, message.substring(0, 100), result.getRecordMetadata().offset()));
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error(String.format("%s -> Unable to send message=[%s] due to : %s", topic, message.substring(0, 100), ex.getMessage()));
                }
            });
        }
    }
}
