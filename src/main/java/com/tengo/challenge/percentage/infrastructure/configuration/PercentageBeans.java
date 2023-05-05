package com.tengo.challenge.percentage.infrastructure.configuration;

import com.tengo.challenge.percentage.application.PercentageService;
import com.tengo.challenge.percentage.domain.PercentageClient;
import com.tengo.challenge.percentage.infrastructure.client.PercentageClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class PercentageBeans {

    @Value("${clients.randominteger.url}")
    private String percentageUrl;

    @Bean
    public PercentageClient percentageClient(RestTemplate client) {
        return new PercentageClientImpl(client, percentageUrl);
    }

    @Bean
    public PercentageService percentageService(PercentageClient percentageClient) {
        return new PercentageService(percentageClient);
    }
}
