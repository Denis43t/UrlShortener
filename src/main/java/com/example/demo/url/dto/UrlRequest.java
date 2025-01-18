package com.example.demo.url.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UrlRequest {
    private String url;
    private String authorizationHeader;
}
