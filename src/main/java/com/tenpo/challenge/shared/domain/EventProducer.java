package com.tenpo.challenge.shared.domain;

public interface EventProducer {

    public void produce(String event, String topic);
}
