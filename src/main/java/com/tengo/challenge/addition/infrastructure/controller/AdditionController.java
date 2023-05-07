package com.tengo.challenge.addition.infrastructure.controller;


import com.tengo.challenge.addition.application.AdditionService;
import com.tengo.challenge.addition.domain.Addition;
import com.tengo.challenge.ratelimiter.domain.RateLimited;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/operations")
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @RateLimited
    @PostMapping("/addition")
    @ResponseBody
    public ResponseEntity<Addition> sum(@Valid @RequestBody AdditionInput additionInput) {
        Addition result = additionService.sum(additionInput.getValue1(), additionInput.getValue2());
        return ResponseEntity.ok().body(result);
    }
}