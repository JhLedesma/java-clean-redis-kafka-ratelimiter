package com.tengo.challenge.exception.infrastructure;

import com.tengo.challenge.exception.domain.ErrorOutput;
import com.tengo.challenge.exception.domain.TooManyRequestsException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(TooManyRequestsException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ErrorOutput handleTooManyRequestsException(TooManyRequestsException exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.TOO_MANY_REQUESTS);
    }

    private ErrorOutput getErrorOutput(TooManyRequestsException exception, WebRequest request, HttpStatus httpStatus) {
        return new ErrorOutput(httpStatus.value(), httpStatus.toString(), request.getDescription(false), exception.getMessage(), ExceptionUtils.getStackTrace(exception));
    }
}
