package com.tengo.challenge.record.infrastructure.configuration;

import com.tengo.challenge.record.application.RecordService;
import com.tengo.challenge.record.domain.RecordRepository;
import com.tengo.challenge.record.infrastructure.repository.RecordDao;
import com.tengo.challenge.record.infrastructure.repository.RecordRepositoryAdapter;
import com.tengo.challenge.shared.domain.ModelMapper;
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
}
