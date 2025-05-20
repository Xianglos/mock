package com.example.mock.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 指定 consumes 为 application/x-www-form-urlencoded
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody MultiValueMap<String, String> formData) {

        // 可以直接将 formData 转为 Map 返回或者封装后再进行其他处理
        messagingTemplate.convertAndSend("/topic/messages", formData.toSingleValueMap());

        return ResponseEntity.ok("Message sent: " + formData.toSingleValueMap());
    }
}