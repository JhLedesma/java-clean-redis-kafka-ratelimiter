package com.tengo.challenge.ratelimiter.infrastructure.controller;

import com.tengo.challenge.ratelimiter.domain.RateLimiter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final com.tengo.challenge.ratelimiter.application.RateLimiter rateLimiter;

    public RateLimiterInterceptor(com.tengo.challenge.ratelimiter.application.RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (rateLimiter != null) {
            this.rateLimiter.checkLimit(request.getRequestURI(), rateLimiter.limit(), rateLimiter.intervalTime());
        }
        return true;
    }
}
