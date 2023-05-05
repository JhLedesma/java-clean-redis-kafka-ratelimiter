package com.tengo.challenge.record.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    private String id;
    private String endpoint;
    private LocalDateTime creationDate;
    private int responseCode;
    private String responseStatus;
    private String response;

    public Record(String endpoint, int responseCode, String responseStatus, String response) {
        this.id = UUID.randomUUID().toString();
        this.endpoint = endpoint;
        this.responseCode = responseCode;
        this.responseStatus = responseStatus;
        this.response = response;
    }
}