package com.example.demo.url;

import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The `UrlController` class handles HTTP requests related to URL shortening and expansion.
 * It serves as a REST controller in the Spring Boot application.
 *
 * The class is annotated with `@RestController` to indicate that it's a controller
 * where every method returns a domain object instead of a view.
 * The `@RequestMapping("/api/v1/url/")` maps all the endpoints to the `/api/v1/url/` base path.
 *
 * This controller has two main endpoints:
 * 1. `shortFromLong` - Shortens a given long URL by using the information in the request body.
 *    It requires the "Authorization" header to process the request. If no header is present,
 *    a blank string will be used by default.
 *
 * 2. `longFromShort` - Expands a shortened URL into a long URL using the information in the request body.
 *
 * Both endpoints return a `ResponseEntity` containing the `UrlResponse` which holds the
 * shortened or expanded URL and associated status.
 */
@RestController
@RequestMapping("/api/v1/url/")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    /**
     * Endpoint to shorten a long URL.
     *
     * This method takes in a `UrlRequest` object as the request body,
     * and reads the "Authorization" header to authenticate the request.
     * If no "Authorization" header is present, it uses an empty string by default.
     *
     * It delegates the URL shortening process to the `UrlService` and returns
     * a `ResponseEntity` containing the shortened URL and its status.
     *
     * @param request The request body containing the long URL and other necessary data.
     * @param header The "Authorization" header used for authentication. Default is an empty string.
     * @return A `ResponseEntity` containing the `UrlResponse` with the shortened URL and its status.
     */
    @PostMapping(path = {"/shortFromLong", "/shortFromLong/"})
    public ResponseEntity<UrlResponse> shortFromLong(@RequestBody UrlRequest request,
                                                     @RequestHeader(value = "Authorization", defaultValue = "") String header) {
        request.setAuthorizationHeader(header);
        UrlResponse response = urlService.getShortUrlFromLongUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Endpoint to expand a shortened URL.
     *
     * This method takes in a `UrlRequest` object as the request body,
     * which contains the shortened URL.
     *
     * It delegates the URL expansion process to the `UrlService` and returns
     * a `ResponseEntity` containing the long URL and its status.
     *
     * @param request The request body containing the shortened URL.
     * @return A `ResponseEntity` containing the `UrlResponse` with the long URL and its status.
     */
    @GetMapping(path = {"/longFromShort", "/longFromShort/"})
    public ResponseEntity<UrlResponse> longFromShort(@RequestBody UrlRequest request) {
        UrlResponse response = urlService.getLongUrlFromShortUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
