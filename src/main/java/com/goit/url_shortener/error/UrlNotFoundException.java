package com.goit.url_shortener.error;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message) {
        super(message);
    }
}
