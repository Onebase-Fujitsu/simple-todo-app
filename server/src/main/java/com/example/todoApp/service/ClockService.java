package com.example.todoApp.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class ClockService implements ClockServiceInterface {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
