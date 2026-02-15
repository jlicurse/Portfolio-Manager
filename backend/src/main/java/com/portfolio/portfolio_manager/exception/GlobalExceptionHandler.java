package com.portfolio.portfolio_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
    This class is a global exception handler for the application.
    It uses @RestControllerAdvice to handle exceptions thrown by controllers and return appropriate HTTP responses.

    "GlobalExceptionHandler" is a common pattern in Spring applications to centralize exception handling logic.
    It allows you to define how different types of exceptions should be handled and what kind of response should be sent back to the client.

*/

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    This function handles MethodArgumentNotValidException, which is thrown when validation on an argument annotated with @Valid fails.
    It constructs a response containing details about the validation errors and returns a 400 Bad Request status
    */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("path", request.getRequestURI());
        response.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(response);
    }
    

    /*
    This function handles generic exceptions that are not specifically handled by other exception handlers.
    It constructs a response containing details about the error and returns a 500 Internal Server Error status.    
    */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
