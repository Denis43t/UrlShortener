package com.example.demo.statistics;

import com.example.demo.security.AuthorizationService;
import com.example.demo.url.Url;
import com.example.demo.url.UrlRepository;
import com.example.demo.url.dto.UrlDto;
import com.example.demo.url.dto.UrlRequest;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.MessageProvider.*;

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

        List<UrlDto> list = new ArrayList<>();

        for (Url url : urls) {
            list.add(new UrlDto(url.getShortUrl(), url.getLongUrl(), url.getVisits()));
        }

        long totalVisits = list.stream()
                .map(UrlDto::getVisits)
                .reduce(0L, Long::sum);

        return StatisticsResponse.success(totalVisits, list);
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
}
