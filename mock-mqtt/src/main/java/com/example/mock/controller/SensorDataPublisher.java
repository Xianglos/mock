package com.example.mock.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.mock.service.MqttPublisherService;

@Component
public class SensorDataPublisher {

    private final MqttPublisherService publisher;

    public SensorDataPublisher(MqttPublisherService publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRate = 5000) // 每 5 秒发布一次
    public void publishSensorData() {
        String topic = "sensors/temperature";
        String payload = "{\"value\":25.6, \"unit\":\"°C\"}";
        publisher.publish(topic, payload);
        System.out.println("定时消息已发布: " + payload);
    }
}