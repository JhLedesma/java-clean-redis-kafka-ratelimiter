package com.tengo.challenge.exception.domain;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}