package com.tengo.challenge.percentage.application;

import com.tengo.challenge.percentage.domain.PercentageClient;

public class PercentageService {

    private final PercentageClient percentageClient;

    public PercentageService(PercentageClient percentageClient) {
        this.percentageClient = percentageClient;
    }

    public Integer getPercentage() {
        return percentageClient.getPercentage();
    }
}
