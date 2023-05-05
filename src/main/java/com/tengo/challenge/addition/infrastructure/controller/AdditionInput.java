package com.tengo.challenge.addition.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionInput {
    @NotNull(message = "Value 1 must not be null")
    @Positive(message = "Value 2 must be positive")
    private Integer value1;
    @NotNull(message = "Value 1 must not be null")
    @Positive(message = "Value 2 must be positive")
    private Integer value2;
}