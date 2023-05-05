package com.tengo.challenge.addition.infrastructure.controller;


import com.tengo.challenge.addition.application.AdditionService;
import com.tengo.challenge.addition.domain.Addition;
import com.tengo.challenge.addition.domain.AdditionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AdditionController {

    private final AdditionService additionService;

    @Autowired
    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }


    @PostMapping("/addition")
    public Addition sum(@RequestBody AdditionInput additionInput) {
        return additionService.sum(additionInput.getValue1(), additionInput.getValue2());
    }
}
