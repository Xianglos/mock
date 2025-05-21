package com.example.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MockMqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockMqttApplication.class, args);

    }

}
