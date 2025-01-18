package com.example.demo.url;

import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;

/**
 * Interface for the URL service, responsible for handling URL shortening and retrieval.
 */
public interface UrlService {

    /**
     * Generates a short URL from a given long URL.
     *
     * @param urlRequest The URL request containing the long URL.
     * @return A response object containing the generated short URL.
     */
    UrlResponse getShortUrlFromLongUrl(UrlRequest urlRequest);

    /**
     * Retrieves the long URL from a given short URL.
     *
     * @param urlRequest The URL request containing the short URL.
     * @return A response object containing the original long URL.
     */
    UrlResponse getLongUrlFromShortUrl(UrlRequest urlRequest);
}
