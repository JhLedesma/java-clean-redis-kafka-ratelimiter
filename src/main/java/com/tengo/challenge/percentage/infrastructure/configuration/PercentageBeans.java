package com.tengo.challenge.percentage.infrastructure.configuration;

import com.tengo.challenge.percentage.domain.PercentageClient;
import com.tengo.challenge.percentage.infrastructure.client.PercentageClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PercentageBeans {

    @Value("clients.randominteger.host")
    private String percentageUrl;

    @Bean
    public PercentageClient percentageClient(RestTemplate client) {
        return new PercentageClientImpl(client, percentageUrl);
    }
}
