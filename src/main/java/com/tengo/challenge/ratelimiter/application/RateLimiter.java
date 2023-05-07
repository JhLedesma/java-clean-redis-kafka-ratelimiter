package com.tengo.challenge.ratelimiter.application;

import com.tengo.challenge.exception.domain.TooManyRequestsException;
import com.tengo.challenge.ratelimiter.domain.RateLimiterRepository;

import java.time.Duration;

public class RateLimiter {

    private final RateLimiterRepository rateLimiterRepository;
    private final int limit;
    private final int timeIntervalInSeconds;

    public RateLimiter(RateLimiterRepository rateLimiterRepository, int limit, int timeIntervalInSeconds) {
        this.rateLimiterRepository = rateLimiterRepository;
        this.limit = limit;
        this.timeIntervalInSeconds = timeIntervalInSeconds;
    }

    public void checkLimit() {
        Long counter = rateLimiterRepository.increment();
        if (counter == 1) {
            rateLimiterRepository.expire(Duration.ofSeconds(timeIntervalInSeconds));
        }
        if (counter > limit) {
            throw new TooManyRequestsException(String.format("Too many requests. Limit: %s request in %s seconds", limit, timeIntervalInSeconds));
        }
    }
}