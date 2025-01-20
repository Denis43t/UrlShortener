package com.goit.url_shortener.url;

import com.goit.url_shortener.url.dto.UrlRequest;
import com.goit.url_shortener.url.dto.UrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
                                                     @RequestHeader(value = "Authorization", defaultValue = "")
                                                     String header) {
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
    @Cacheable(value = "urlCache", key = "#request.getUrl")
    @GetMapping(path = {"/longFromShort", "/longFromShort/"})
    public ResponseEntity<UrlResponse> longFromShort(@RequestBody UrlRequest request) {
        UrlResponse response = urlService.getLongUrlFromShortUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Handles the PATCH request to update an existing URL.
     *
     * <p>This endpoint allows a client to update the details of an existing URL using the provided
     * {@link UrlRequest}. The request must include the necessary data for updating the URL, and
     * the `Authorization` header is required for authorization purposes. The method processes
     * the update, invokes the corresponding service layer, and returns a {@link UrlResponse} with
     * the result status.</p>
     *
     * @param request   The {@link UrlRequest} object containing the updated URL details.
     * @param header    The `Authorization` header passed by the client to authenticate the request.
     * @return          A {@link ResponseEntity} containing the {@link UrlResponse} with the result status.
     */
    @PatchMapping(path = {"/update", "/update/"})
    public ResponseEntity<UrlResponse> updateUrl(@RequestBody UrlRequest request,
                                                 @RequestHeader(value = "Authorization", defaultValue = "")
                                                 String header) {
        request.setAuthorizationHeader(header);
        UrlResponse response = urlService.updateUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Handles the DELETE request to remove an existing URL.
     *
     * <p>This endpoint allows a client to delete a URL by sending a {@link UrlRequest} containing
     * the URL details. The `Authorization` header is required for authorization purposes. The method
     * processes the delete request, interacts with the service layer, and returns a {@link UrlResponse}
     * with the result status.</p>
     *
     * @param request   The {@link UrlRequest} object containing the details of the URL to be deleted.
     * @param header    The `Authorization` header passed by the client to authenticate the request.
     * @return          A {@link ResponseEntity} containing the {@link UrlResponse} with the result status.
     */
    @DeleteMapping(path = {"/delete", "/delete/"})
    public ResponseEntity<UrlResponse> deleteUrl(@RequestBody UrlRequest request,
                                                 @RequestHeader(value = "Authorization", defaultValue = "")
                                                 String header) {
        request.setAuthorizationHeader(header);
        UrlResponse response = urlService.deleteUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
