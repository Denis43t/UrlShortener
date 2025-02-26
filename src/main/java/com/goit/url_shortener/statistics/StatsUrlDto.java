package com.goit.url_shortener.statistics;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO class for URL statistics, including short URL, long URL, visits,
 * activation status, creation, and expiration timestamps.
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsUrlDto {
    private String shortUrl;
    private String longUrl;
    private long visits;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
