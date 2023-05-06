package com.tengo.challenge.percentage.application;

import com.tengo.challenge.exception.domain.BadGatewayException;
import com.tengo.challenge.percentage.domain.PercentageCache;
import com.tengo.challenge.percentage.domain.PercentageClient;
import io.vavr.control.Try;

public class PercentageService {

    private final PercentageClient percentageClient;
    private final PercentageCache percentageCache;

    public PercentageService(PercentageClient percentageClient, PercentageCache percentageCache) {
        this.percentageClient = percentageClient;
        this.percentageCache = percentageCache;
    }

    public Integer getPercentage() {
        return percentageCache.getPercentage().orElseGet(this::getFromClient);
    }

    private Integer getFromClient() {
        return Try.of(percentageClient::getPercentage)
                .map(percentageCache::save)
                .getOrElse(getLastUsed());
    }

    private Integer getLastUsed() {
        return percentageCache.getLastUsedPercentage().orElseThrow(() -> new BadGatewayException("Client Server Error"));
    }
}
