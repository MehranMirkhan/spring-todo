package com.fpt.common;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

public class NotFoundException extends AbstractException {
    public NotFoundException(Map<String, Serializable> payload) {
        super(HttpStatus.NOT_FOUND, payload);
    }

    public NotFoundException(String message, Map<String, Serializable> payload) {
        super(HttpStatus.NOT_FOUND, message, payload);
    }

    public NotFoundException(String message, Throwable cause, Map<String, Serializable> payload) {
        super(HttpStatus.NOT_FOUND, message, cause, payload);
    }
}