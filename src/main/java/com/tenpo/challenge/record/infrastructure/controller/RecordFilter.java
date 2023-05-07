package com.tenpo.challenge.record.infrastructure.controller;

import com.tenpo.challenge.record.application.RecordProducer;
import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.shared.application.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class RecordFilter implements Filter {

    private final RecordProducer recordProducer;
    private final JsonParser jsonParser;

    public RecordFilter(RecordProducer recordProducer, JsonParser jsonParser) {
        this.recordProducer = recordProducer;
        this.jsonParser = jsonParser;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ContentCachingResponseWrapper httpResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
        String endpoint = httpRequest.getRequestURI();
        try {
            chain.doFilter(request, httpResponse);
        } finally {
            if (isValidPath(endpoint)) {
                Record record = new Record(endpoint, httpResponse.getStatus(), HttpStatus.valueOf(httpResponse.getStatus()).toString(), getResponseBody(httpResponse));
                recordProducer.produce(record);
            }
            httpResponse.copyBodyToResponse();
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper httpResponse) {
        String responseBody = null;
        int statusCode = httpResponse.getStatus();
        if (statusCode >= 200 && statusCode < 300) {
            responseBody = jsonParser.toJson(new String(httpResponse.getContentAsByteArray()));
        }
        return responseBody;
    }

    private boolean isValidPath(String endpoint) {
        return Stream.of("api-docs", "swagger-ui").noneMatch(endpoint::contains);
    }

}