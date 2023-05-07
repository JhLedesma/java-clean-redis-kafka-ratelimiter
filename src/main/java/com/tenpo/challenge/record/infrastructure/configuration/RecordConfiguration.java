package com.tenpo.challenge.record.infrastructure.configuration;

import com.tenpo.challenge.record.application.RecordProducer;
import com.tenpo.challenge.record.application.RecordService;
import com.tenpo.challenge.record.domain.RecordRepository;
import com.tenpo.challenge.record.infrastructure.consumer.RecordConsumer;
import com.tenpo.challenge.record.infrastructure.controller.RecordFilter;
import com.tenpo.challenge.record.infrastructure.repository.RecordDao;
import com.tenpo.challenge.record.infrastructure.repository.RecordRepositoryAdapter;
import com.tenpo.challenge.shared.application.JsonParser;
import com.tenpo.challenge.shared.domain.EventProducer;
import com.tenpo.challenge.shared.domain.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@ServletComponentScan
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
    public RecordProducer recordProducer(EventProducer eventProducer, JsonParser jsonParser) {
        return new RecordProducer(eventProducer, jsonParser);
    }

    @Bean
    public RecordConsumer recordConsumer(RecordService recordService, JsonParser jsonParser) {
        return new RecordConsumer(recordService, jsonParser);
    }

    @Bean
    public FilterRegistrationBean<RecordFilter> responseLoggingFilter(JsonParser jsonParser, RecordProducer recordProducer) {
        FilterRegistrationBean<RecordFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RecordFilter(recordProducer, jsonParser));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
