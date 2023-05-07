package com.tengo.challenge.percentage.infrastructure.configuration;

import com.tengo.challenge.percentage.application.PercentageService;
import com.tengo.challenge.percentage.domain.PercentageCache;
import com.tengo.challenge.percentage.domain.PercentageClient;
import com.tengo.challenge.percentage.infrastructure.client.PercentageClientImpl;
import com.tengo.challenge.percentage.infrastructure.repository.PercentageCacheAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
@EnableRedisRepositories
public class PercentageConfiguration {

    @Value("${clients.randominteger.url}")
    private String percentageUrl;
    @Value("${clients.randominteger.cachedseconds}")
    private Integer cachedSeconds;
    @Value("${clients.randominteger.mock}")
    private boolean mock;

    @Bean
    public PercentageClient percentageClient(RestTemplate client) {
        return new PercentageClientImpl(client, percentageUrl, mock);
    }

    @Bean
    public PercentageCache percentageCache(RedisTemplate<String, Integer> redisTemplate) {
        return new PercentageCacheAdapter(redisTemplate, cachedSeconds);
    }

    @Bean
    public PercentageService percentageService(PercentageClient percentageClient, PercentageCache percentageCache) {
        return new PercentageService(percentageClient, percentageCache);
    }
}
