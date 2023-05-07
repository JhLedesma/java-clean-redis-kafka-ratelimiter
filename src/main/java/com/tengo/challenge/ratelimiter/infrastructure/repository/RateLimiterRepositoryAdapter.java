package com.tengo.challenge.ratelimiter.infrastructure.repository;

import com.tengo.challenge.ratelimiter.domain.RateLimiterRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RateLimiterRepositoryAdapter implements RateLimiterRepository {

    private final RedisTemplate<String, Integer> redisTemplate;
    private static final String KEY = "rate_limit";

    public RateLimiterRepositoryAdapter(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long increment() {
        return redisTemplate.opsForValue().increment(KEY);
    }

    @Override
    public void expire(Duration timeout) {
        redisTemplate.expire(KEY, timeout);
    }
}
