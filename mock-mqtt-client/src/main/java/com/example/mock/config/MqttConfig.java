package com.example.mock.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    private static final String BROKER_URL = "ws://localhost:1883/mqtt";
    private static final String CLIENT_ID = "spring-client-" + System.currentTimeMillis();
    private static final String TOPIC = "sensors/temperature";
    private static final String USERNAME = "panda";
    private static final String PASSWORD = "123456";

    @Bean
    public IMqttClient mqttClient() {
        try {
            IMqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(PASSWORD.toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

            // 关键点：先设置回调，再连接
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("连接丢失: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("收到消息 => 主题: " + topic + ", 内容: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            client.connect(options);
            client.subscribe(TOPIC, 1); // 显式指定 QoS=1
            System.out.println("MQTT 已连接并订阅: " + TOPIC);
            return client;
        } catch (MqttException e) {
            System.err.println("MQTT 初始化失败: " + e.getMessage());
            return null;
        }
    }
}