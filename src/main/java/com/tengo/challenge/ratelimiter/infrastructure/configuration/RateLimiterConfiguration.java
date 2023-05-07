package com.tengo.challenge.ratelimiter.infrastructure.configuration;

import com.tengo.challenge.ratelimiter.application.RateLimiter;
import com.tengo.challenge.ratelimiter.domain.RateLimiterRepository;
import com.tengo.challenge.ratelimiter.infrastructure.repository.RateLimiterRepositoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RateLimiterConfiguration {

    @Value("${ratelimiter.limit}")
    private int limit;
    @Value("${ratelimiter.intervalTime}")
    private int timeIntervalInSeconds;

    @Bean
    public RateLimiterRepository rateLimiterRepository(RedisTemplate<String, Integer> redisTemplate) {
        return new RateLimiterRepositoryAdapter(redisTemplate);
    }

    @Bean
    public RateLimiter rateLimiter(RateLimiterRepository rateLimiterRepository) {
        return new RateLimiter(rateLimiterRepository, limit, timeIntervalInSeconds);
    }
}