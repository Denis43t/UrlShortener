package com.example.demo.statistics.controller;


@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlStatisticsResponseDTO> getStatisticsByShortUrl(
            @PathVariable String shortUrl) {
        var statistics = statisticsService.getStatisticsByShortUrl(shortUrl);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UrlStatisticsResponseDTO>> getUserStatistics() {
        var statistics = statisticsService.getStatisticsForUser();
        return ResponseEntity.ok(statistics);
    }
}
