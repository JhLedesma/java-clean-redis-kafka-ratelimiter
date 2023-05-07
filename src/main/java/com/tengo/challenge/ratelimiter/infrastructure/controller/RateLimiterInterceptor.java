package com.tengo.challenge.ratelimiter.infrastructure.controller;

import com.tengo.challenge.ratelimiter.application.RateLimiter;
import com.tengo.challenge.ratelimiter.domain.RateLimited;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    public RateLimiterInterceptor(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);
        if (rateLimited != null) {
            rateLimiter.checkLimit();
        }
        return true;
    }
}
