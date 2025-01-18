package com.example.demo.statistics;

import com.example.demo.url.dto.UrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling URL statistics.
 * This controller provides endpoints to fetch various statistics about shortened URLs.
 */
@RestController
@RequestMapping("/api/v1/url/stats/")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Endpoint to retrieve all URLs associated with the authenticated user.
     *
     * @param header The Authorization header containing user credentials.
     * @return ResponseEntity containing the status and list of URLs.
     */
    @GetMapping(path = {"/all", "all/"})
    public ResponseEntity<StatisticsResponse> allUrls(
            @RequestHeader(value = "Authorization", defaultValue = "")
            String header) {
        UrlRequest request = new UrlRequest();
        request.setAuthorizationHeader(header);
        StatisticsResponse response = statisticsService.getAllUrlsByUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Endpoint to fetch the number of visits for a given short URL.
     *
     * @param request The request containing the short URL.
     * @param header  The Authorization header containing user credentials.
     * @return ResponseEntity containing the status and the visit count for the URL.
     */
    @GetMapping(path = {"/visits", "visits/"})
    public ResponseEntity<StatisticsResponse> visitsByShortUrl(
            @RequestBody UrlRequest request,
            @RequestHeader(value = "Authorization", defaultValue = "")
            String header) {
        request.setAuthorizationHeader(header);
        StatisticsResponse response = statisticsService.getVisitsByShortUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
