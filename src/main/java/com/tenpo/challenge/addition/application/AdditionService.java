package com.tenpo.challenge.addition.application;

import com.tenpo.challenge.addition.domain.Addition;
import com.tenpo.challenge.percentage.application.PercentageService;

public class AdditionService {

    private final PercentageService percentageService;

    public AdditionService(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    public Addition sum(Integer value1, Integer value2) {
        Integer percentage =  percentageService.getPercentage();
        Integer sum = value1 + value1;
        double decimalPercentage = percentage / 100.0;
        double sumPercentage = sum * decimalPercentage;
        double total = sum + sumPercentage;
        return new Addition(value1, value2, percentage, total);
    }
}
