package com.example.demo.url.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UrlDto {
    private String shortUrl;
    private String longUrl;
    private long visits;
}
