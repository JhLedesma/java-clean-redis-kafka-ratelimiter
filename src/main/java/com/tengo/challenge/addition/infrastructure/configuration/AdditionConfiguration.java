package com.tengo.challenge.addition.infrastructure.configuration;

import com.tengo.challenge.addition.application.AdditionService;
import com.tengo.challenge.percentage.application.PercentageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdditionConfiguration {

    @Bean
    public AdditionService additionService(PercentageService percentageService) {
        return new AdditionService(percentageService);
    }
}
