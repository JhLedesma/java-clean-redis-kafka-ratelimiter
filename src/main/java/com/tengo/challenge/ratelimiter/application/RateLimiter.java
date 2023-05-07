package com.tengo.challenge.ratelimiter.application;

import com.tengo.challenge.exception.domain.TooManyRequestsException;
import com.tengo.challenge.ratelimiter.domain.RateLimiterRepository;

import java.time.Duration;

public class RateLimiter {

    private final RateLimiterRepository rateLimiterRepository;

    public RateLimiter(RateLimiterRepository rateLimiterRepository) {
        this.rateLimiterRepository = rateLimiterRepository;
    }

    public void checkLimit(String endpoint, int limit, int timeIntervalInSeconds) {
        Long counter = rateLimiterRepository.increment(endpoint);
        if (counter == 1) {
            rateLimiterRepository.expire(endpoint, Duration.ofSeconds(timeIntervalInSeconds));
        }
        if (counter > limit) {
            throw new TooManyRequestsException(String.format("Too many requests. Limit: %s request in %s seconds", limit, timeIntervalInSeconds));
        }
    }
}