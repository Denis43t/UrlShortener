package com.example.demo;




import com.example.urlshortener.statistics.dto.UrlStatisticsResponseDTO;
import com.example.urlshortener.statistics.dto.UserStatisticsResponseDTO;
import com.example.urlshortener.statistics.entity.UrlStatistics;
import com.example.urlshortener.statistics.repository.UrlStatisticsRepository;
import com.example.urlshortener.user.entity.User;
import com.example.urlshortener.user.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsServiceImplTest {

    private final UrlStatisticsRepository urlStatisticsRepository = mock(UrlStatisticsRepository.class);
    private final UserService userService = mock(UserService.class);
    private final StatisticsServiceImpl statisticsService = new StatisticsServiceImpl(urlStatisticsRepository, userService);

    @Test
    void getStatisticsByShortUrl_ShouldReturnStatistics_WhenUrlExists() {
        // Arrange
        String shortUrl = "abc123";
        UrlStatistics mockStatistics = new UrlStatistics();
        mockStatistics.setShortUrl(shortUrl);
        mockStatistics.setOriginalUrl("https://example.com");
        mockStatistics.setClickCount(42);
        mockStatistics.setCreatedAt(LocalDateTime.now());

        when(urlStatisticsRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockStatistics));

        // Act
        UrlStatisticsResponseDTO result = statisticsService.getStatisticsByShortUrl(shortUrl);

        // Assert
        assertNotNull(result);
        assertEquals(shortUrl, result.getShortUrl());
        assertEquals("https://example.com", result.getOriginalUrl());
        assertEquals(42, result.getClickCount());
        verify(urlStatisticsRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    void getStatisticsByShortUrl_ShouldThrowException_WhenUrlDoesNotExist() {
        // Arrange
        String shortUrl = "nonexistent";
        when(urlStatisticsRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                statisticsService.getStatisticsByShortUrl(shortUrl)
        );

        assertEquals("Statistics not found for URL: nonexistent", exception.getMessage());
        verify(urlStatisticsRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    void getStatisticsForUser_ShouldReturnStatisticsList_WhenUserHasUrls() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        when(userService.getCurrentUser()).thenReturn(mockUser);

        UrlStatistics url1 = new UrlStatistics();
        url1.setShortUrl("abc123");
        url1.setClickCount(10);

        UrlStatistics url2 = new UrlStatistics();
        url2.setShortUrl("xyz456");
        url2.setClickCount(20);

        when(urlStatisticsRepository.findAllByUserId(mockUser.getId()))
                .thenReturn(List.of(url1, url2));

        // Act
        List<UserStatisticsResponseDTO> result = statisticsService.getStatisticsForUser();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("abc123", result.get(0).getShortUrl());
        assertEquals(10, result.get(0).getClickCount());

        assertEquals("xyz456", result.get(1).getShortUrl());
        assertEquals(20, result.get(1).getClickCount());

        verify(userService, times(1)).getCurrentUser();
        verify(urlStatisticsRepository, times(1)).findAllByUserId(mockUser.getId());
    }

    @Test
    void getStatisticsForUser_ShouldReturnEmptyList_WhenUserHasNoUrls() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(urlStatisticsRepository.findAllByUserId(mockUser.getId())).thenReturn(List.of());

        // Act
        List<UserStatisticsResponseDTO> result = statisticsService.getStatisticsForUser();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService, times(1)).getCurrentUser();
        verify(urlStatisticsRepository, times(1)).findAllByUserId(mockUser.getId());
    }
}
