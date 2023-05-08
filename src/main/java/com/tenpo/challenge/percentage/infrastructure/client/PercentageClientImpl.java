package com.tenpo.challenge.percentage.infrastructure.client;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.percentage.domain.PercentageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PercentageClientImpl implements PercentageClient {

    private final RestTemplate client;
    private final String url;
    private final boolean useMock;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PercentageClientImpl(RestTemplate client, String url, boolean useMock) {
        this.client = client;
        this.url = url;
        this.useMock = useMock;
    }

    @Retryable(value = RuntimeException.class, maxAttemptsExpression = "#{${clients.randominteger.retries}}", backoff = @Backoff(delayExpression = "#{${clients.randominteger.retrydelay}}"))
    public Integer getPercentage() {
        Integer percentage = useMock ? generateRandomPercentage() : fetchPercentageFromServer();
        logger.info("Retrieved percentage from client: {}", percentage);
        return percentage;
    }

    private Integer fetchPercentageFromServer() {
        logger.info("Fetching percentage from server...");
        Integer percentage = client.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {})
                .getBody()
                .stream()
                .findFirst().orElseThrow(() -> new BadGatewayException("Server Error Response"));
        logger.info("Fetched percentage from server: {}", percentage);
        return percentage;
    }

    private Integer generateRandomPercentage() {
        Integer percentage = ThreadLocalRandom.current().nextInt(1, 101);
        logger.info("Generated random percentage: {}", percentage);
        return percentage;
    }

}
