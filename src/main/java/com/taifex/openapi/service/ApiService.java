package com.taifex.openapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private final RestTemplate restTemplate;

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendGetRequest(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}

