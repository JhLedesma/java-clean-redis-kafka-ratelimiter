package com.tengo.challenge.addition.infrastructure.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengo.challenge.addition.application.AdditionService;
import com.tengo.challenge.addition.domain.Addition;
import com.tengo.challenge.record.application.RecordService;
import com.tengo.challenge.record.domain.Record;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/operations")
public class AdditionController {

    private final AdditionService additionService;
    private final RecordService recordService;
    private final ObjectMapper objectMapper;


    public AdditionController(AdditionService additionService, RecordService recordService, ObjectMapper objectMapper) {
        this.additionService = additionService;
        this.recordService = recordService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/addition")
    public Addition sum(@Valid @RequestBody AdditionInput additionInput) throws JsonProcessingException {
        Addition addition = additionService.sum(additionInput.getValue1(), additionInput.getValue2());
        recordService.save(new Record("/test", 200, HttpStatus.OK.toString(), objectMapper.writeValueAsString(addition)));
        return addition;
    }
}
