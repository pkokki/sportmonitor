package com.panos.sportmonitor.webapi.kafka;

import com.panos.sportmonitor.common.Event;
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
public class OverviewProducer {
    private static final Logger logger = LoggerFactory.getLogger(OverviewProducer.class);

    @Value(value = "${kafka.overview.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void sendMessage(Event event) {
        logger.info(String.format("%s -> Sending event -> %s", topic, event));
        ListenableFuture<SendResult<String, Event>>  future = this.kafkaTemplate.send(topic, event);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Event>>() {

            @Override
            public void onSuccess(SendResult<String, Event> result) {
                logger.info(String.format("%s -> Sent    event -> %s with offset=[%d]", topic, event, result.getRecordMetadata().offset()));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(String.format("%s -> Unable to send event=[%s] due to : %s", topic, event, ex.getMessage()));
            }
        });
    }
}
