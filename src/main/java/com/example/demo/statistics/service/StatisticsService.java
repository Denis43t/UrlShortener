package com.example.demo.statistics.service;


import com.example.urlshortener.statistics.dto.UrlStatisticsResponseDTO;
import com.example.urlshortener.statistics.dto.UserStatisticsResponseDTO;

import java.util.List;

public interface StatisticsService {
    UrlStatisticsResponseDTO getStatisticsByShortUrl(String shortUrl);

    List<UserStatisticsResponseDTO> getStatisticsForUser();
}
