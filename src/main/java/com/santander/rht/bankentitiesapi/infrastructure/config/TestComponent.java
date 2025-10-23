package com.santander.rht.bankentitiesapi.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@Slf4j
public class TestComponent {

    public TestComponent() {
        log.info("TestComponent constructor called!");
    }

    @PostConstruct
    public void init() {
        log.info("TestComponent @PostConstruct called!");
    }
}
