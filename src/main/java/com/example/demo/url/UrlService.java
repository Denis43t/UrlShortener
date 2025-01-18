package com.example.demo.url;

import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;

public interface UrlService {
    UrlResponse getShortUrlFromLongUrl(UrlRequest urlRequest);
    UrlResponse getLongUrlFromShortUrl(UrlRequest urlRequest);
}
