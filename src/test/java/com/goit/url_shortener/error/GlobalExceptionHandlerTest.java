package com.goit.url_shortener.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    @Test
    void testHandleUrlNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UrlNotFoundException exception = new UrlNotFoundException("URL not found");

        ResponseEntity<ErrorResponse> response = handler.handleUrlNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponse errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals("URL not found", errorBody.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorBody.getStatus());
        assertNotNull(errorBody.getTimestamp());
    }

    @Test
    void testHandleGenericException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("Some generic error");

        ResponseEntity<ErrorResponse> response = handler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponse errorBody = response.getBody();
        assertNotNull(errorBody);
        assertEquals("Some generic error", errorBody.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorBody.getStatus());

        assertNotNull(errorBody.getTimestamp());
    }


}