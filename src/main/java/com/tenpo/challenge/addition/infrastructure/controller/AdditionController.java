package com.tenpo.challenge.addition.infrastructure.controller;


import com.tenpo.challenge.addition.application.AdditionService;
import com.tenpo.challenge.addition.domain.Addition;
import com.tenpo.challenge.ratelimiter.domain.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/operations")
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @RateLimiter(limit = 3, intervalTime = 60)
    @PostMapping("/addition")
    public ResponseEntity<Addition> sum(@Valid @RequestBody AdditionInput additionInput) {
        Addition result = additionService.sum(additionInput.getValue1(), additionInput.getValue2());
        return ResponseEntity.ok().body(result);
    }
}