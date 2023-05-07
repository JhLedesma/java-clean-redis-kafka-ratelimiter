package com.tengo.challenge.ratelimiter.domain;

import java.time.Duration;

public interface RateLimiterRepository {

    public Long increment(String key);
    public void expire(String key, Duration timeout);
}
