package com.example.mock.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import io.moquette.broker.security.IAuthenticator;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//配置类 MqttBrokerConfig.java
@Configuration
public class MqttBrokerConfig {
    private Server mqttBroker;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @PostConstruct
    public void startBroker() throws IOException {
        // 手动创建配置并设置默认属性
        Properties props = new Properties();
        props.put(BrokerConstants.PORT_PROPERTY_NAME, "1883");
        props.put(BrokerConstants.WEB_SOCKET_PORT_PROPERTY_NAME, "11883");
        props.put(BrokerConstants.HOST_PROPERTY_NAME, "0.0.0.0");
        props.put(BrokerConstants.WEB_SOCKET_PATH_PROPERTY_NAME, "/mqtt");
        props.put(BrokerConstants.ALLOW_ANONYMOUS_PROPERTY_NAME, "false");

        props.put("mqtt.protocol.versions", "3,4,5");

//        props.put(BrokerConstants.MQTT_VERSION_PROPERTY_NAME, "5");
//        props.put(BrokerConstants.ALLOWED_PROTOCOL_VERSIONS_PROPERTY_NAME, "3,4,5"); // 关键配置
//
//        // 如果启用了WebSocket需同步配置
//        props.put(BrokerConstants.WS_MQTT_VERSION_PROPERTY_NAME, "5");

        final IConfig config = new MemoryConfig(props); // 使用Properties初始化

//        IConfig config = new ResourceLoaderConfig(new ClasspathResourceLoader("moquette.conf"));

        mqttBroker = new Server();
        // 直接设置认证器
        // mqttBroker.setAuthenticator(new AuthenticatorImpl());
        mqttBroker.startServer(config, null, null, new AuthenticatorImpl(), null);
    }

    // 认证器实现
    class AuthenticatorImpl implements IAuthenticator {
        @Override
        public boolean checkValid(String clientId, String username, byte[] password) {
            return MqttBrokerConfig.this.username.equals(username) && Arrays.equals(MqttBrokerConfig.this.password.getBytes(), password);
        }
    }

    @PreDestroy
    public void stopBroker() {
        mqttBroker.stopServer();
    }
}