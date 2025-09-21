package com.springboot.dietapplication.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exception) {
        Map<String, Object> messageBody = new HashMap<>();

        messageBody.put("status", exception.getBody().getStatus());
        messageBody.put("message", exception.getReason());
        messageBody.put("time", new Date());

        return new ResponseEntity<>(messageBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        Map<String, Object> messageBody = new HashMap<>();

        messageBody.put("message", exception.getLocalizedMessage());
        messageBody.put("time", new Date());

        return new ResponseEntity<>(messageBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
