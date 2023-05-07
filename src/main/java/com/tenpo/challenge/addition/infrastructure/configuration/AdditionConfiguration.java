package com.tenpo.challenge.addition.infrastructure.configuration;

import com.tenpo.challenge.addition.application.AdditionService;
import com.tenpo.challenge.percentage.application.PercentageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdditionConfiguration {

    @Bean
    public AdditionService additionService(PercentageService percentageService) {
        return new AdditionService(percentageService);
    }
}
