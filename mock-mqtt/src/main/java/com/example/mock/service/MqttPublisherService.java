package com.example.mock.service;

import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    private final MqttPahoMessageHandler mqttHandler;

    public MqttPublisherService(MqttPahoClientFactory factory) {
        this.mqttHandler = new MqttPahoMessageHandler("publisherClient", factory);
        this.mqttHandler.setAsync(true);

        // 使用 MQTT 专用消息转换器
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true); // 关键配置：强制转换为字节数组
        this.mqttHandler.setConverter(converter);
    }

    /**
     * 向指定主题发布消息
     * 
     * @param topic   目标主题（如 "sensors/temperature"）
     * @param payload 消息内容（字符串或字节数组）
     */
    public void publish(String topic, Object payload) {
        Message<?> message = MessageBuilder.withPayload(payload).setHeader(MqttHeaders.TOPIC, topic) // 动态指定主题
                .build();
        mqttHandler.handleMessage(message);
    }
}