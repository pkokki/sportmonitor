package com.panos.sportmonitor;

import com.panos.sportmonitor.spark.KafkaReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiveDataServer {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(LiveDataServer.class, args);
    }
}
