package com.goit.url_shortener.error;


import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * Represents a standard error response containing a message and HTTP status.
 */
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus status;
}
