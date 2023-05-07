package com.tenpo.challenge.shared.infrastructure;

import com.tenpo.challenge.shared.domain.EventProducer;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer implements EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(String event, String topic) {
        kafkaTemplate.send(topic, event);
    }
}