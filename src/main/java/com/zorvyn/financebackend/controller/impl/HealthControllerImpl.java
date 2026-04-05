package com.zorvyn.financebackend.controller.impl;

import com.zorvyn.financebackend.controller.api.HealthControllerApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthControllerImpl implements HealthControllerApi {

    @Override
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
