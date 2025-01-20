package com.goit.url_shortener.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * GlobalExceptionHandler is a centralized exception handling class that captures all unhandled exceptions and errors
 * that may occur throughout the application. It serves as the final filter to catch any unforeseen exceptions
 * that were not previously handled.
 *
 * When an exception is thrown, this handler logs the error message and stack trace, and constructs a standardized
 * response using ErrorResponse with a failure status. The response includes an appropriate HTTP status and an error
 * message.
 *
 * This handler ensures that all unexpected errors are gracefully handled, providing a consistent and informative
 * response to clients.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse.builder().message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}