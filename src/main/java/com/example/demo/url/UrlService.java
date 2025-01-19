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

    /**
     * Method to handle the update URL operation.
     *
     * <p>This method processes a request to update an existing URL. The request contains
     * the necessary data for updating the URL. The implementation should validate the
     * request and perform the necessary updates, returning a {@link UrlResponse} with the
     * result status and relevant information.</p>
     *
     * @param request   The {@link UrlRequest} object containing the updated URL details.
     * @return          A {@link UrlResponse} containing the result status and updated URL details.
     */
    UrlResponse updateUrl(UrlRequest request);

    /**
     * Method to handle the delete URL operation.
     *
     * <p>This method processes a request to delete an existing URL. The request includes
     * the details of the URL to be deleted. The implementation should validate the request,
     * perform the necessary deletion, and return a {@link UrlResponse} with the result status.</p>
     *
     * @param request   The {@link UrlRequest} object containing the details of the URL to be deleted.
     * @return          A {@link UrlResponse} indicating the result status of the deletion.
     */
    UrlResponse deleteUrl(UrlRequest request);
}
