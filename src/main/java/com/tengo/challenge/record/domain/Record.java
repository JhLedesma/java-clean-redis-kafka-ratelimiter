package com.tengo.challenge.record.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    private String endpoint;
    private LocalDateTime creationDate;
    private int responseCode;
    private String responseStatus;
    private String response;
}