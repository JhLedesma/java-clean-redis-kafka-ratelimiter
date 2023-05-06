package com.tengo.challenge.record.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengo.challenge.record.application.RecordService;
import com.tengo.challenge.record.domain.RecordRepository;
import com.tengo.challenge.record.infrastructure.controller.RecordFilter;
import com.tengo.challenge.record.infrastructure.repository.RecordDao;
import com.tengo.challenge.record.infrastructure.repository.RecordRepositoryAdapter;
import com.tengo.challenge.shared.domain.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class RecordConfiguration {

    @Bean
    public RecordRepository recordRepository(RecordDao recordDao, ModelMapper modelMapper) {
        return new RecordRepositoryAdapter(recordDao, modelMapper);
    }

    @Bean
    public RecordService recordService(RecordRepository recordRepository) {
        return new RecordService(recordRepository);
    }

    @Bean
    public FilterRegistrationBean<RecordFilter> responseLoggingFilter(ObjectMapper objectMapper, RecordService recordService) {
        FilterRegistrationBean<RecordFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RecordFilter(objectMapper, recordService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
