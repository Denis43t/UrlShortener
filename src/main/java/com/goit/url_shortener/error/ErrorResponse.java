package com.goit.url_shortener.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int status;

    private String message;

    private LocalDateTime timestamp;

    public ErrorResponse(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}