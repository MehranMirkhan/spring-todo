package com.fpt.common;

import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                                           .id(UUID.randomUUID())
                                           .message(ex.getMessage())
                                           .build();
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<ErrorResponse> handleAbstractException(AbstractException ex) {
        ErrorResponse error = ErrorResponse.builder()
                                           .id(ex.getId())
                                           .message(ex.getMessage())
                                           .payload(ex.getPayload())
                                           .build();
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @Builder
    record ErrorResponse(UUID id, String message, Map<String, Serializable> payload) {
    }
}
