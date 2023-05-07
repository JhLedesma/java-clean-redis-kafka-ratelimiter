package com.tenpo.challenge.shared.infrastructure.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateBean {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(10000)).build();
    }
}
