package com.example.mock;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 开启简单代理，消息发送到以 /topic 开头的地址将被代理
        registry.enableSimpleBroker("/topic");
        // 指定以 /app 作为前缀，客户端发送的消息需要以 /app 开头
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个端点，客户端通过这个端点进行连接，并支持 SockJS 回退方式
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}