package com.example.demo.url.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlResponse {
    private String shortUrl;
    private String longUrl;
    private String message;

    @JsonIgnore
    private HttpStatus status;

    public UrlResponse(String shortUrl, String longUrl, HttpStatus status) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.status = status;
    }

    public UrlResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static UrlResponse success(String shortUrl, String longUrl) {
        return new UrlResponse(shortUrl, longUrl, HttpStatus.OK);
    }

    public static UrlResponse failed(String message, HttpStatus status) {
        return new UrlResponse(message, status);
    }
}
