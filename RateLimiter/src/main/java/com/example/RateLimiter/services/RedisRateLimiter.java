package com.example.RateLimiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisRateLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isAllowed(String key, int limit, int timeWindowSeconds) {
        Long currentCount = redisTemplate.opsForValue().increment(key);
        if (currentCount != null && currentCount == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(timeWindowSeconds));
        }
        return currentCount <= limit;
    }

}