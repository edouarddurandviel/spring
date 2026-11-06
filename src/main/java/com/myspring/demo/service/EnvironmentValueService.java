package com.myspring.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentValueService {

    @Value("${app.environment}")
    private String environment;

    public String getEnvironment() {
        return environment;
    }
}
