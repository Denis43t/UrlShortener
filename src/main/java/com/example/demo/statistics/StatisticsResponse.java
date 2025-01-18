package com.example.demo.statistics;

import com.example.demo.url.dto.UrlDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Represents the response object for URL statistics.
 * This class is used to encapsulate the data and status of URL statistics responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StatisticsResponse {

    /**
     * The total number of visits for URLs.
     */
    private Long visits;

    /**
     * A list of URL DTOs containing detailed information about each URL.
     */
    private List<UrlDto> urls;

    /**
     * A message providing additional information about the response.
     */
    private String message;

    /**
     * The HTTP status of the response.
     */
    @JsonIgnore
    private HttpStatus status;

    /**
     * Constructs a successful response containing the total visits and list of URLs.
     *
     * @param visits The total number of visits.
     * @param urls A list of URL DTOs.
     * @param status The HTTP status of the response.
     */
    public StatisticsResponse(Long visits, List<UrlDto> urls, HttpStatus status) {
        this.visits = visits;
        this.urls = urls;
        this.status = status;
    }

    /**
     * Constructs a failure response with a specific message and HTTP status.
     *
     * @param message The failure message.
     * @param status The HTTP status of the response.
     */
    public StatisticsResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Static method to create a successful statistics response.
     *
     * @param visits The total number of visits.
     * @param urls A list of URL DTOs.
     * @return A new StatisticsResponse instance representing success.
     */
    public static StatisticsResponse success(long visits, List<UrlDto> urls) {
        return new StatisticsResponse(visits, urls, HttpStatus.OK);
    }

    /**
     * Static method to create a failed statistics response.
     *
     * @param message The failure message.
     * @param status The HTTP status of the response.
     * @return A new StatisticsResponse instance representing failure.
     */
    public static StatisticsResponse failed(String message, HttpStatus status) {
        return new StatisticsResponse(message, status);
    }
}
