package com.fpt.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
public class AbstractException extends ResponseStatusException {
    private final UUID                      id;
    private final Map<String, Serializable> payload;

    public AbstractException(HttpStatus status, Map<String, Serializable> payload) {
        super(status);
        id = UUID.randomUUID();
        this.payload = payload;
    }

    public AbstractException(HttpStatus status, String reason, Map<String, Serializable> payload) {
        super(status, reason);
        id = UUID.randomUUID();
        this.payload = payload;
    }

    public AbstractException(HttpStatus status, String reason, Throwable cause, Map<String, Serializable> payload) {
        super(status, reason, cause);
        id = UUID.randomUUID();
        this.payload = payload;
    }

    public AbstractException(int rawStatusCode, String reason, Throwable cause, Map<String, Serializable> payload) {
        super(rawStatusCode, reason, cause);
        id = UUID.randomUUID();
        this.payload = payload;
    }
}
