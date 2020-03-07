package com.panos.sportmonitor.spark;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${spark.start}")
    private boolean sparkStart;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (sparkStart) {
            ApplicationContext ac = event.getApplicationContext();
            SparkKafkaStreamExecutor sparkKafkaStreamExecutor = ac.getBean(SparkKafkaStreamExecutor.class);
            Thread thread = new Thread(sparkKafkaStreamExecutor);
            thread.start();
        }
    }
}
