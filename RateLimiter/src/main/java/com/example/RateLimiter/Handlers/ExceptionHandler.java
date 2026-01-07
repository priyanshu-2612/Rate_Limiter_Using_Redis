package com.example.RateLimiter.Handlers;

import com.example.RateLimiter.exceptions.RateLimitExceedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RateLimitExceedException.class)
    public ResponseEntity<String> handleRateLimitExceeedException(RateLimitExceedException rateLimitExceedException) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(rateLimitExceedException.getMessage());
    }

}