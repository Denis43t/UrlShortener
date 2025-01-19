package com.example.demo.url.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for handling URL-related requests.
 * Contains the long URL, optional expiration date, and the authorization header.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UrlRequest {
    private String url;
    private LocalDateTime expiresAt;
    private String authorizationHeader;
}
