package com.tengo.challenge.addition.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdditionInput {
    private Integer value1;
    private Integer value2;
}