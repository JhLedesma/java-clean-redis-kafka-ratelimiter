package com.tenpo.challenge.record.infrastructure.consumer;

import com.tenpo.challenge.record.application.RecordService;
import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.shared.application.JsonParser;
import org.springframework.kafka.annotation.KafkaListener;

public class RecordConsumer {

    private final RecordService recordService;
    private final JsonParser jsonParser;

    public RecordConsumer(RecordService recordService, JsonParser jsonParser) {
        this.recordService = recordService;
        this.jsonParser = jsonParser;
    }

    @KafkaListener(topics = "records-topic")
    public void consume(String message) {
        Record record = jsonParser.fromJson(message, Record.class);
        recordService.save(record);
    }
}
