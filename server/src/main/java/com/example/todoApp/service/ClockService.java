package com.example.todoApp.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

public interface ClockService {
    Instant now();
}

@Service
class ClockServiceImpl implements ClockService{

    @Override
    public Instant now() {
        return Instant.now();
    }
}
