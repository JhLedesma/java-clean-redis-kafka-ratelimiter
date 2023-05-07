package com.tenpo.challenge.record.application;

import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.shared.application.JsonParser;
import com.tenpo.challenge.shared.domain.EventProducer;
import org.springframework.beans.factory.annotation.Value;

public class RecordProducer {

    private final EventProducer eventProducer;
    private final JsonParser jsonParser;
    @Value("${spring.kafka.topics.record}")
    private String topic;

    public RecordProducer(EventProducer eventProducer, JsonParser jsonParser) {
        this.eventProducer = eventProducer;
        this.jsonParser = jsonParser;
    }

    public Record produce(Record record) {
        eventProducer.produce(jsonParser.toJson(record), topic);
        return record;
    }
}
