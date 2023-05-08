package com.tenpo.challenge.percentage.application;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.percentage.domain.PercentageCache;
import com.tenpo.challenge.percentage.domain.PercentageClient;
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
                .getOrElse(this::getLastUsed);
    }

    private Integer getLastUsed() {
        return percentageCache.getLastUsedPercentage().orElseThrow(() -> new BadGatewayException("Client Server Error"));
    }
}
