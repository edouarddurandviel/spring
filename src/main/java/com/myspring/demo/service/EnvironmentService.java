package com.myspring.demo.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService {

    private final Environment environment;

    public EnvironmentService(Environment environment) {
        this.environment = environment;
    }

    public String getActiveProfile() {
        // spring core
        return environment.getActiveProfiles()[0];
    }
}
