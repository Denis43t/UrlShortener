package com.example.demo.url;

import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/url/")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping(path = {"/shortFromLong", "/shortFromLong/"})
    public ResponseEntity<UrlResponse> shortFromLong(@RequestBody UrlRequest request,
                                                     @RequestHeader(value = "Authorization")
                                                     String header) {
        request.setAuthorizationHeader(header);
        UrlResponse response = urlService.getShortUrlFromLongUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(path = {"/longFromShort", "/longFromShort/"})
    public ResponseEntity<UrlResponse> longFromShort(@RequestBody UrlRequest request){
        UrlResponse response = urlService.getLongUrlFromShortUrl(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
