package com.example.demo.statistics;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatsUrlDto {
    private String shortUrl;
    private String longUrl;
    private long visits;
    private boolean isActive;
    private LocalDateTime createdAt;
}
