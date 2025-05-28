package com.example.mock.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class MachineService {
    private final RestTemplate restTemplate = new RestTemplate();

    public void postMachineData() {
        // 1. 构造请求参数
        String url = "http://localhost:9090/machine";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 使用 LinkedMultiValueMap 代替 Lambda 表达式
        java.util.Map<String, String> requestBody = new java.util.HashMap<>();
        requestBody.put("param1", "value1");
        requestBody.put("param2", "value2");

        // 2. 封装请求实体
        HttpEntity<java.util.Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // 3. 发送 POST 请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // 4. 监控返回值
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response Body: " + response.getBody());
                System.out.println("Status Code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }
    }
}