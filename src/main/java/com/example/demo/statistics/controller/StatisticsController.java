package com.example.demo.statistics.controller;


import com.example.urlshortener.statistics.dto.UrlStatisticsResponseDTO;
import com.example.urlshortener.statistics.dto.UserStatisticsResponseDTO;
import com.example.urlshortener.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    // Отримання статистики по конкретному короткому URL
    @GetMapping("/url/{shortUrl}")
    public ResponseEntity<UrlStatisticsResponseDTO> getUrlStatistics(@PathVariable String shortUrl) {
        return ResponseEntity.ok(statisticsService.getStatisticsByShortUrl(shortUrl));
    }

    // Отримання загальної статистики по всіх URL авторизованого користувача
    @GetMapping("/user")
    public ResponseEntity<List<UserStatisticsResponseDTO>> getUserStatistics() {
        return ResponseEntity.ok(statisticsService.getStatisticsForUser());
    }
}

