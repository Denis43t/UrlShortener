package com.example.demo.statistics;


import com.example.demo.url.dto.UrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/api/v1/url/stats/"))
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;


    @GetMapping(path = {"/all", "all/"})
    public ResponseEntity<StatisticsResponse> allUrls(
            @RequestHeader(value = "Authorization")
            String header) {
        UrlRequest request = new UrlRequest();
        request.setAuthorizationHeader(header);
        StatisticsResponse response = statisticsService.getAllUrlsByUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping(path = {"/visits", "visits/"})
    public ResponseEntity<StatisticsResponse> visitsByShortUrl(
            @RequestBody UrlRequest request,
            @RequestHeader(value = "Authorization")
            String header) {
        request.setAuthorizationHeader(header);
        StatisticsResponse response = statisticsService.getVisitsByShortUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
