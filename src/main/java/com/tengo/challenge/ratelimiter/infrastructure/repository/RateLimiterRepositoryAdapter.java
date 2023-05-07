package com.tengo.challenge.ratelimiter.infrastructure.repository;

import com.tengo.challenge.ratelimiter.domain.RateLimiterRepository;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RateLimiterRepositoryAdapter implements RateLimiterRepository {

    private final RedisTemplate<String, Integer> redisTemplate;
    private static final String PREFIX = "rate_limit";

    public RateLimiterRepositoryAdapter(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(PREFIX + key);
    }

    @Override
    public void expire(String key, Duration timeout) {
        redisTemplate.expire(PREFIX + key, timeout);
    }
}
