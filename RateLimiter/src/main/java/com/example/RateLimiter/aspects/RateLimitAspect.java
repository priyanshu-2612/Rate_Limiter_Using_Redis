package com.example.RateLimiter.aspects;

import com.example.RateLimiter.annotations.RateLimit;
import com.example.RateLimiter.exceptions.RateLimitExceedException;
import com.example.RateLimiter.services.RedisRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private RedisRateLimiter redisRateLimiter;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Around("@annotation(com.example.RateLimiter.annotations.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature =
                (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        String clientIp = httpServletRequest.getRemoteAddr();
        String redisKey = "ratelimit:" + clientIp + ":" + method.getName();

        boolean allowed = redisRateLimiter.isAllowed(
                redisKey,
                rateLimit.limit(),
                rateLimit.timeWindowSeconds()
        );

        if (!allowed) {
            throw new RateLimitExceedException("Too Many Requests. Please try again.");
        }

        return joinPoint.proceed();
    }
}
