package com.fpt.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Map;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
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