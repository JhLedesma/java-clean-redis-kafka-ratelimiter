package com.tengo.challenge.shared.infrastructure.configuration;

import com.tengo.challenge.shared.domain.ModelMapper;
import com.tengo.challenge.shared.infrastructure.ModelMapperAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapperAdapter(new org.modelmapper.ModelMapper());
    }
}
