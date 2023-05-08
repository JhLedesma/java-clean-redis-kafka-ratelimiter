package com.tenpo.challenge.exception.infrastructure;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.exception.domain.InternalServerErrorException;
import com.tenpo.challenge.shared.application.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ClientErrorHandler implements ResponseErrorHandler {

    private final JsonParser jsonParser;

    public ClientErrorHandler(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        HttpStatus httpStatus = httpResponse.getStatusCode();

        String responseBody = jsonParser.toJson(new String(httpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8));
        String errorMessage = String.format("Failed to execute a request to external service. Status code: %s, Response body: %s", httpStatus, responseBody);
        throw mapToSystemException(httpStatus, errorMessage);
    }

    private RuntimeException mapToSystemException(HttpStatus httpStatus, String errorMessage) {
        if (httpStatus.is5xxServerError()) {
            return new BadGatewayException(errorMessage);
        } else {
            return new InternalServerErrorException(errorMessage);
        }
    }
}
