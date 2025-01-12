package com.example.demo.statistics.repository;


import com.example.urlshortener.statistics.entity.UrlStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlStatisticsRepository extends JpaRepository<UrlStatistics, Long> {
    Optional<UrlStatistics> findByShortUrl(String shortUrl);

    List<UrlStatistics> findAllByUserId(Long userId);
}
