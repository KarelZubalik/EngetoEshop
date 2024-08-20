package com.example.EngetoEshop.RestControlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

    @RestController
    public class ConfigController {

        @Value("${server.address}")
        private String host;

        @Value("${server.port}")
        private int port;

        @GetMapping("/api/config")
        public Map<String, Object> getConfig() {
            Map<String, Object> config = new HashMap<>();
            config.put("host", host);
            config.put("port", port);
            return config;
        }
    }
