package com.goit.url_shortener.statistics;

import com.goit.url_shortener.security.AuthorizationService;
import com.goit.url_shortener.url.Url;
import com.goit.url_shortener.url.UrlRepository;
import com.goit.url_shortener.url.dto.UrlRequest;
import com.goit.url_shortener.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.goit.url_shortener.util.MessageProvider.*;

/**
 * Service class responsible for handling URL statistics.
 * This service interacts with the URL repository and the authorization service to retrieve and process URL statistics.
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    /**
     * The URL repository for accessing URL data.
     */
    private final UrlRepository urlRepository;

    /**
     * The authorization service to handle user authentication.
     */
    private final AuthorizationService authorizationService;

    /**
     * Retrieves all URLs associated with the authenticated user and calculates total visits.
     *
     * @param request The URL request containing authorization information.
     * @return A response object containing the total visits and a list of URLs.
     */
    @Transactional
    public StatisticsResponse getAllUrlsByUser(UrlRequest request) {
        Optional<User> userOptional = authorizationService.getAuthorizedUser(request.getAuthorizationHeader());

        if (userOptional.isEmpty()) {
            return StatisticsResponse.failed(NOT_AUTHENTICATED_MESSAGE, HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        List<Url> urls = urlRepository.findAllUrlsByUsername(user.getUsername());

        if (urls.isEmpty()) {
            return StatisticsResponse.failed(URL_LIST_EMPTY_MESSAGE, HttpStatus.NOT_FOUND);
        }

        List<StatsUrlDto> list = createStatsUrlDtos(urls);

        long totalVisits = list.stream()
                .map(StatsUrlDto::getVisits)
                .reduce(0L, Long::sum);

        return StatisticsResponse.success(totalVisits, list);
    }

    /**
     * Retrieves all active URLs associated with the authenticated user.
     * An active URL is defined as one where the expiration time has not passed.
     * The method performs user authorization and fetches URLs filtered by activity status.
     *
     * @param request The UrlRequest object containing the authorization header.
     * @return A StatisticsResponse object containing the total number of visits and the list of active URLs.
     *         Returns an error response if the user is not authenticated or if no URLs are found.
     */
    @Transactional
    public StatisticsResponse getActiveUrlsByUser(UrlRequest request) {
        Optional<User> userOptional = authorizationService.getAuthorizedUser(request.getAuthorizationHeader());

        if (userOptional.isEmpty()) {
            return StatisticsResponse.failed(NOT_AUTHENTICATED_MESSAGE, HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        List<Url> urls = urlRepository.findAllUrlsByUsername(user.getUsername());

        if (urls.isEmpty()) {
            return StatisticsResponse.failed(URL_LIST_EMPTY_MESSAGE, HttpStatus.NOT_FOUND);
        }

        List<StatsUrlDto> activeUrls = createActiveStatsUrlDtos(urls);

        long totalVisits = activeUrls.stream()
                .map(StatsUrlDto::getVisits)
                .reduce(0L, Long::sum);

        return StatisticsResponse.success(totalVisits, activeUrls);
    }


    /**
     * Retrieves the number of visits for a specific short URL associated with the authenticated user.
     *
     * @param request The URL request containing the short URL and authorization information.
     * @return A response object containing the number of visits.
     */
    @Transactional
    public StatisticsResponse getVisitsByShortUrl(UrlRequest request) {
        Optional<User> userOptional = authorizationService.getAuthorizedUser(request.getAuthorizationHeader());

        if (userOptional.isEmpty()) {
            return StatisticsResponse.failed(NOT_AUTHENTICATED_MESSAGE, HttpStatus.UNAUTHORIZED);
        }

        Optional<Url> urlOptional = urlRepository.findUrlByShortUrl(request.getUrl());

        if (urlOptional.isEmpty()) {
            return StatisticsResponse.failed(URL_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }

        Url url = urlOptional.get();

        return StatisticsResponse.success(url.getVisits(), null);
    }

    /**
     * Converts a list of URLs into a list of StatsUrlDtos.
     * Each URL is processed to determine if it is still active based on its creation and expiration times.
     *
     * @param urls The list of URLs to be converted.
     * @return A list of StatsUrlDto objects representing the processed URLs.
     */
    private List<StatsUrlDto> createStatsUrlDtos(List<Url> urls) {
        List<StatsUrlDto> list = new ArrayList<>();
        for (Url url : urls) {
            LocalDateTime expiresAt = url.getExpiresAt();
            boolean isActive = expiresAt == null || expiresAt.isAfter(LocalDateTime.now());
            list.add(
                    new StatsUrlDto(
                            url.getShortUrl(),
                            url.getLongUrl(),
                            url.getVisits(),
                            isActive,
                            url.getCreatedAt(),
                            expiresAt));
        }
        return list;
    }

    /**
     * Creates a list of StatsUrlDtos for URLs that are currently active.
     * Active URLs are those where the creation time is before the expiration time.
     *
     * @param urls The list of URLs to be filtered and converted.
     * @return A list of active StatsUrlDto objects.
     */
    private List<StatsUrlDto> createActiveStatsUrlDtos(List<Url> urls) {
        List<StatsUrlDto> list = new ArrayList<>();
        for (Url url : urls) {
            LocalDateTime expiresAt = url.getExpiresAt();
            if (expiresAt == null || expiresAt.isAfter(LocalDateTime.now())) {
                list.add(
                        new StatsUrlDto(
                                url.getShortUrl(),
                                url.getLongUrl(),
                                url.getVisits(),
                                true,
                                url.getCreatedAt(),
                                expiresAt));
            }
        }
        return list;
    }
}
