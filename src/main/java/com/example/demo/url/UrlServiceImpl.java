package com.example.demo.url;

import com.example.demo.security.AuthorizationService;
import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.util.MessageProvider.*;

/**
 * The `UrlServiceImpl` class implements the `UrlService` interface and provides the business logic
 * for URL shortening and expansion. It handles URL-related operations such as validating input,
 * generating shortened URLs, retrieving long URLs from shortened URLs, and interacting with the
 * database through the `UrlRepository`.
 *
 * The class is annotated with `@Service` to indicate it's a service component in the Spring framework.
 */
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final LongUrlValidator validator;
    private final ShortUrlGenerator urlGenerator;
    private final AuthorizationService authorizationService;

    /**
     * Generates a shortened URL from a given long URL.
     *
     * This method performs the following steps:
     * 1. Validates the long URL to ensure it's in the correct format.
     * 2. Checks if the user is authorized using the `AuthorizationService`.
     * 3. Generates a unique shortened URL using `ShortUrlGenerator`.
     * 4. Persists the URL entity into the database using `UrlRepository`.
     *
     * If the long URL is invalid, the method returns a `UrlResponse` with a `BAD_REQUEST` status.
     * If the user is not authenticated, the method returns a `UrlResponse` with an `UNAUTHORIZED` status.
     * Otherwise, it successfully saves the URL and returns a response with the shortened URL.
     *
     * @param request The `UrlRequest` containing the long URL and authorization header.
     * @return A `UrlResponse` indicating success with the shortened URL or failure with an appropriate status.
     */
    @Override
    @Transactional
    public UrlResponse getShortUrlFromLongUrl(UrlRequest request) {
        String longUrl = request.getUrl();

        if (longUrl == null || longUrl.isEmpty()) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!validator.isValid(longUrl)) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = authorizationService.getAuthorizedUser(request.getAuthorizationHeader());

        if (userOptional.isEmpty()) {
            return UrlResponse.failed(NOT_AUTHENTICATED_MESSAGE, HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        String shortUrl = urlGenerator.generateShortUrl();

        Url url = Url.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .user(user)
                .build();

        urlRepository.save(url);

        return UrlResponse.success(shortUrl, null);
    }

    /**
     * Retrieves the long URL from a given shortened URL.
     *
     * This method performs the following steps:
     * 1. Validates the shortened URL.
     * 2. Searches for the corresponding `Url` entity in the database using `UrlRepository`.
     * 3. If found, increments the visit count.
     * 4. Returns a `UrlResponse` with the long URL, or a failure response if the URL is not found.
     *
     * If the shortened URL is invalid or not found, the method returns a `UrlResponse` with appropriate status codes.
     *
     * @param request The `UrlRequest` containing the shortened URL.
     * @return A `UrlResponse` indicating success with the long URL or failure with an appropriate status.
     */
    @Override
    @Transactional
    public UrlResponse getLongUrlFromShortUrl(UrlRequest request) {
        String shortUrl = request.getUrl();

        if (shortUrl == null || shortUrl.isEmpty()) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        Optional<Url> urlOptional = urlRepository.findUrlByShortUrl(shortUrl);
        if (urlOptional.isEmpty()) {
            return UrlResponse.failed(URL_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }

        Url url = urlOptional.get();
        url.setVisits(url.getVisits() + 1);
        urlRepository.save(url);
        return UrlResponse.success(null, url.getLongUrl());
    }
}

