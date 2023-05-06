package com.tengo.challenge.record.infrastructure.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tengo.challenge.record.application.RecordService;
//import com.tengo.challenge.record.domain.Record;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class RecordInterceptor implements HandlerInterceptor {
//
//    private final RecordService recordService;
//    private final ObjectMapper objectMapper;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public RecordInterceptor(RecordService recordService, ObjectMapper objectMapper) {
//        this.recordService = recordService;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        ContentCachingResponseWrapper httpResponse = new ContentCachingResponseWrapper(response);
//        String endpoint = request.getRequestURI();
//        //if (!endpoint.contains("/api/v1/records")) {
//            String responseBody = new String(httpResponse.getContentAsByteArray());
//            recordService.save(new Record(endpoint, httpResponse.getStatus(), HttpStatus.valueOf(httpResponse.getStatus()).toString(), responseBody));
//        //}
//    }
//
//}
