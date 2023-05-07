package com.tenpo.challenge.percentage.infrastructure.repository;

import com.tenpo.challenge.percentage.domain.PercentageCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Optional;

public class PercentageCacheAdapter implements PercentageCache {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final Integer cachedSeconds;

    public PercentageCacheAdapter(RedisTemplate<String, Integer> redisTemplate, Integer cachedSeconds) {
        this.redisTemplate = redisTemplate;
        this.cachedSeconds = cachedSeconds;
    }

    @Override
    public Integer save(Integer percentage) {
        redisTemplate.opsForValue().set("percentage", percentage, Duration.ofSeconds(cachedSeconds));
        redisTemplate.opsForValue().set("last_percentage", percentage);
        return percentage;
    }

    @Override
    public Optional<Integer> getPercentage() {
        return Optional.ofNullable(redisTemplate.opsForValue().get("percentage"));
    }

    @Override
    public Optional<Integer> getLastUsedPercentage() {
        return Optional.ofNullable(redisTemplate.opsForValue().get("last_percentage"));
    }
}
