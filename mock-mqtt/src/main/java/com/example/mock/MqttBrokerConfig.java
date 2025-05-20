package com.example.mock;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.moquette.BrokerConstants;
import io.moquette.broker.Server;

@Configuration
public class MqttBrokerConfig {

    @Bean
    public Server mqttBroker() throws IOException {
        Server server = new Server();
        Properties props = new Properties();
        // 禁用TCP端口
        props.setProperty("port", "0");
        // 配置WebSocket端口和路径
        props.setProperty(BrokerConstants.WEB_SOCKET_PORT_PROPERTY_NAME, "1883");
        props.setProperty(BrokerConstants.WEB_SOCKET_PATH_PROPERTY_NAME, "/mqtt");
        props.setProperty("host", "0.0.0.0");
        server.startServer(props);
        return server;
    }
}