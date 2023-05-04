package com.tengo.challenge.addition.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Addition {
    private Integer value1;
    private Integer value2;
    private Float percentage;
    private Float total;
}