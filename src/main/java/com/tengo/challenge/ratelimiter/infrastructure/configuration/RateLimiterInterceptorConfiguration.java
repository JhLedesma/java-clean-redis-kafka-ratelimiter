package com.tengo.challenge.ratelimiter.infrastructure.configuration;

import com.tengo.challenge.ratelimiter.infrastructure.controller.RateLimiterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RateLimiterInterceptorConfiguration implements WebMvcConfigurer {

    private final RateLimiterInterceptor rateLimiterInterceptor;

    public RateLimiterInterceptorConfiguration(RateLimiterInterceptor rateLimiterInterceptor) {
        this.rateLimiterInterceptor = rateLimiterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimiterInterceptor).excludePathPatterns("/**/swagger-ui/**");
    }
}
