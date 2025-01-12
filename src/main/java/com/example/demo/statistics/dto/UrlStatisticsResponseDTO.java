package com.example.demo.statistics.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// DTO для статистики конкретного URL
@Data
@Builder
public class UrlStatisticsResponseDTO {
    private String shortUrl;
    private String originalUrl;
    private int clickCount;
    private LocalDateTime createdAt;
}

// DTO для загальної статистики користувача
@Data
@Builder
public class UserStatisticsResponseDTO {
    private String shortUrl;
    private int clickCount;
}

