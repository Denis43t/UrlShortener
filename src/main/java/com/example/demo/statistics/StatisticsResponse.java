package com.example.demo.statistics;


import com.example.demo.url.dto.UrlDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StatisticsResponse {
    private Long visits;
    private List<UrlDto> urls;
    private String message;

    @JsonIgnore
    private HttpStatus status;

    public StatisticsResponse(Long visits, List<UrlDto> urls, HttpStatus status) {
        this.visits = visits;
        this.urls = urls;
        this.status = status;
    }

    public StatisticsResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static StatisticsResponse success(long visits, List<UrlDto> urls) {
        return new StatisticsResponse(visits, urls, HttpStatus.OK);
    }

    public static StatisticsResponse failed(String message, HttpStatus status) {
        return new StatisticsResponse(message, status);
    }
}
