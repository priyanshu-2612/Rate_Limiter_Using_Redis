package com.example.RateLimiter.controllers;

import com.example.RateLimiter.annotations.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RateLimit(limit = 5, timeWindowSeconds = 60)
    @GetMapping("/api/data")
    public String getData() {
        return "Hello World!";
    }

}
