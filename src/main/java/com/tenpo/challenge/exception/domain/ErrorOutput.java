package com.tenpo.challenge.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorOutput {
    private int code;
    private String error;
    private String endpoint;
    private String message;
    private String stackTrace;
}
