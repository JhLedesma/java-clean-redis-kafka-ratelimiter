package com.tenpo.challenge.percentage.application;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.percentage.domain.PercentageCache;
import com.tenpo.challenge.percentage.domain.PercentageClient;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PercentageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PercentageClient percentageClient;
    private final PercentageCache percentageCache;

    public PercentageService(PercentageClient percentageClient, PercentageCache percentageCache) {
        this.percentageClient = percentageClient;
        this.percentageCache = percentageCache;
    }

    public Integer getPercentage() {
        Integer percentage = percentageCache.getPercentage().orElseGet(this::getFromClient);
        logger.info("Retrieved percentage: {}", percentage);
        return percentage;
    }

    private Integer getFromClient() {
        Integer percentage = Try.of(percentageClient::getPercentage)
                .map(percentageCache::save)
                .getOrElse(this::getLastUsed);
        logger.info("Retrieved percentage from client: {}", percentage);
        return percentage;
    }

    private Integer getLastUsed() {
        logger.warn("Failed to retrieve percentage from client, falling back to last used percentage");
        return percentageCache.getLastUsedPercentage().orElseThrow(() -> new BadGatewayException("Client Server Error"));
    }
}
