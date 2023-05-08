package com.tenpo.challenge.exception.infrastructure;

import com.tenpo.challenge.exception.domain.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorOutput handleTooManyRequestsException(BadRequestException exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorOutput handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorOutput handleTooManyRequestsException(ResourceNotFoundException exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChallangeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorOutput handleTooManyRequestsException(ChallangeException exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorOutput handleTooManyRequestsException(Exception exception, WebRequest request) {
        return getErrorOutput(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorOutput getErrorOutput(Throwable exception, WebRequest request, HttpStatus httpStatus) {
        return new ErrorOutput(httpStatus.value(), httpStatus.toString(), request.getDescription(false), exception.getMessage(), ExceptionUtils.getStackTrace(exception));
    }
}
