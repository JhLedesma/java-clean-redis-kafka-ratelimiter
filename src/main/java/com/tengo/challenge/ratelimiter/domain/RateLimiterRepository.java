package com.tengo.challenge.ratelimiter.domain;

import java.time.Duration;

public interface RateLimiterRepository {

    public Long increment();
    public void expire(Duration timeout);
}
