package com.tengo.challenge.addition.infrastructure.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengo.challenge.addition.application.AdditionService;
import com.tengo.challenge.addition.domain.Addition;
import com.tengo.challenge.record.application.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public ResponseEntity<Addition> sum(@Valid @RequestBody AdditionInput additionInput) {
        Addition result = additionService.sum(additionInput.getValue1(), additionInput.getValue2());
        return ResponseEntity.ok().body(result);
    }
}
