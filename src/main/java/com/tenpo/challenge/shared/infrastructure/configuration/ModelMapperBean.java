package com.tenpo.challenge.shared.infrastructure.configuration;

import com.tenpo.challenge.shared.domain.ModelMapper;
import com.tenpo.challenge.shared.infrastructure.ModelMapperAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapperAdapter(new org.modelmapper.ModelMapper());
    }
}
