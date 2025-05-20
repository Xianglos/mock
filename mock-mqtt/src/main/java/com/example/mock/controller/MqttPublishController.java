package com.example.mock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mock.service.MqttPublisherService;

@RestController
public class MqttPublishController {

    private final MqttPublisherService publisher;

    public MqttPublishController(MqttPublisherService publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestParam String topic, @RequestParam String message) {
        publisher.publish(topic, message);
        return "消息已发布到主题: " + topic;
    }
}