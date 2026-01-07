package com.example.RateLimiter.exceptions;

public class RateLimitExceedException extends RuntimeException {
    public RateLimitExceedException(String message) {
        super(message);
    }
}