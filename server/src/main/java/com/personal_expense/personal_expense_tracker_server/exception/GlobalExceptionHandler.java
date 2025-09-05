package com.personal_expense.personal_expense_tracker_server.exception;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Map<String, String>> handleEntityExistsException(EntityExistsException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
