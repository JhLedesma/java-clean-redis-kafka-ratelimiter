package com.tenpo.challenge.percentage.infrastructure.client;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.percentage.domain.PercentageClient;
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

    public PercentageClientImpl(RestTemplate client, String url, boolean useMock) {
        this.client = client;
        this.url = url;
        this.useMock = useMock;
    }

    @Retryable(value = RuntimeException.class, maxAttemptsExpression = "#{${clients.randominteger.retries}}", backoff = @Backoff(delayExpression = "#{${clients.randominteger.retrydelay}}"))
    public Integer getPercentage() {
        return useMock ? generateRandomPercentage() : fetchPercentageFromServer();
    }

    private Integer fetchPercentageFromServer() {
        return client.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {})
                .getBody()
                .stream()
                .findFirst().orElseThrow(() -> new BadGatewayException(""));
    }

    private Integer generateRandomPercentage() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }

}
