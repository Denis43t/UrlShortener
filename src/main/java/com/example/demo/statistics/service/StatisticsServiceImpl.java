package com.example.demo.statistics.service;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final UrlService urlService;

    public StatisticsServiceImpl(UrlService urlService) {
        this.urlService = urlService;
    }

    @Override
    public UrlStatisticsResponseDTO getStatisticsByShortUrl(String shortUrl) {
        var url = urlService.getUrlByShortCode(shortUrl); // Метод з UrlService
        if (url == null) {
            throw new RuntimeException("URL not found for short code: " + shortUrl);
        }

        return new UrlStatisticsResponseDTO(
                url.getShortUrl(),
                url.getOriginalUrl(),
                url.getClickCount(),
                url.getCreatedAt()
        );
    }

    @Override
    public List<UrlStatisticsResponseDTO> getStatisticsForUser() {
        var currentUser = ... // Отримати поточного користувача через UserService
        var urls = urlService.getUrlsByUserId(currentUser.getId());

        return urls.stream()
                .map(url -> new UrlStatisticsResponseDTO(
                        url.getShortUrl(),
                        url.getOriginalUrl(),
                        url.getClickCount(),
                        url.getCreatedAt()
                ))
                .toList();
    }
}
