package com.tengo.challenge.record.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tengo.challenge.record.application.RecordService;
import com.tengo.challenge.record.domain.Record;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecordFilter implements Filter {

    private final RecordService recordService;
    private final ObjectMapper objectMapper;

    public RecordFilter(ObjectMapper objectMapper, RecordService recordService) {
        this.recordService = recordService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ContentCachingResponseWrapper httpResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
        String endpoint = httpRequest.getRequestURI();
        try {
            chain.doFilter(request, httpResponse);
        } finally {
            String responseBody = objectMapper.writeValueAsString(new String(httpResponse.getContentAsByteArray()));
            recordService.save(new Record(endpoint, httpResponse.getStatus(), HttpStatus.valueOf(httpResponse.getStatus()).toString(), responseBody));
            httpResponse.copyBodyToResponse();
        }
    }
}