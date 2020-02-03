package com.panos.sportmonitor;

import com.panos.sportmonitor.spark.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiveDataServer {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(LiveDataServer.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
