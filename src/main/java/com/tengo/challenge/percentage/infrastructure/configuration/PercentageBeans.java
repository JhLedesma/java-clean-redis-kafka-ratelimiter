package com.tengo.challenge.percentage.infrastructure.configuration;

import com.tengo.challenge.percentage.application.PercentageService;
import com.tengo.challenge.percentage.domain.PercentageCache;
import com.tengo.challenge.percentage.domain.PercentageClient;
import com.tengo.challenge.percentage.infrastructure.client.PercentageClientImpl;
import com.tengo.challenge.percentage.infrastructure.repository.PercentageCacheAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
//@EnableCaching
@EnableRedisRepositories
public class PercentageBeans {

    @Value("${clients.randominteger.url}")
    private String percentageUrl;
    @Value("${clients.randominteger.cachedSeconds}")
    private Integer cachedSeconds;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Integer.class));
        return redisTemplate;
    }

    @Bean
    public PercentageClient percentageClient(RestTemplate client) {
        return new PercentageClientImpl(client, percentageUrl);
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
