package com.zorvyn.financebackend.controller.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface HealthControllerApi {

    // Simple health check endpoint to confirm the server is running.
    @GetMapping("/health")
    Map<String, String> health();
}
