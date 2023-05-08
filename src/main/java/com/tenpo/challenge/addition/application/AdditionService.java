package com.tenpo.challenge.addition.application;

import com.tenpo.challenge.addition.domain.Addition;
import com.tenpo.challenge.exception.domain.BadRequestException;
import com.tenpo.challenge.percentage.application.PercentageService;

public class AdditionService {

    private final PercentageService percentageService;

    public AdditionService(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    public Addition sum(Integer value1, Integer value2) {
        validateValues(value1, value2);
        validatePercentage(percentageService.getPercentage());

        int sum = value1 + value2;
        double decimalPercentage = percentageService.getPercentage() / 100.0;
        double sumPercentage = sum * decimalPercentage;
        double total = sum + sumPercentage;
        return new Addition(value1, value2, percentageService.getPercentage(), total);
    }

    private void validateValues(Integer value1, Integer value2) {
        if (value1 == null || value2 == null || value1 <= 0 || value2 <= 0) {
            throw new BadRequestException("The values must be non-null positive integers");
        }
    }

    private void validatePercentage(Integer percentage) {
        if (percentage == null || percentage <= 0) {
            throw new BadRequestException("The percentage must be a non-null positive integer");
        }
    }
}
