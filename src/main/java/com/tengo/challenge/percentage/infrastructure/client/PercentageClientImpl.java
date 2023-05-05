package com.tengo.challenge.percentage.infrastructure.client;

import com.tengo.challenge.exception.domain.BadGatewayException;
import com.tengo.challenge.percentage.domain.PercentageClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class PercentageClientImpl implements PercentageClient {

    private final RestTemplate client;
    private final String url;

    public PercentageClientImpl(RestTemplate client, String url) {
        this.client = client;
        this.url = url;
    }

    @Retryable(value = RuntimeException.class, maxAttemptsExpression = "#{${clients.randominteger.retries}}", backoff = @Backoff(delayExpression = "#{${clients.randominteger.retrydelay}}"))
    public Integer getPercentage() {
        return client.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {})
                .getBody()
                .stream()
                .findFirst().orElseThrow( () -> new BadGatewayException(""));
    }
}
