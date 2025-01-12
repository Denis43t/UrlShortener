package com.example.demo.statistics.service;


import com.example.urlshortener.statistics.dto.UrlStatisticsResponseDTO;
import com.example.urlshortener.statistics.dto.UserStatisticsResponseDTO;
import com.example.urlshortener.statistics.repository.UrlStatisticsRepository;
import com.example.urlshortener.user.service.UserService; // Сервіс для роботи з користувачами
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UrlStatisticsRepository urlStatisticsRepository;
    private final UserService userService; // Для отримання авторизованого користувача

    @Override
    public UrlStatisticsResponseDTO getStatisticsByShortUrl(String shortUrl) {
        var statistics = urlStatisticsRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Statistics not found for URL: " + shortUrl));

        return UrlStatisticsResponseDTO.builder()
                .shortUrl(statistics.getShortUrl())
                .originalUrl(statistics.getOriginalUrl())
                .clickCount(statistics.getClickCount())
                .createdAt(statistics.getCreatedAt())
                .build();
    }

    @Override
    public List<UserStatisticsResponseDTO> getStatisticsForUser() {
        var currentUser = userService.getCurrentUser(); // Метод для отримання авторизованого користувача

        return urlStatisticsRepository.findAllByUserId(currentUser.getId())
                .stream()
                .map(stat -> UserStatisticsResponseDTO.builder()
                        .shortUrl(stat.getShortUrl())
                        .clickCount(stat.getClickCount())
                        .build())
                .collect(Collectors.toList());
    }
}
