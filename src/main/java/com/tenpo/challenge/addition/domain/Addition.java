package com.tenpo.challenge.addition.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Addition {
    private Integer value1;
    private Integer value2;
    private Integer percentage;
    private double total;
}