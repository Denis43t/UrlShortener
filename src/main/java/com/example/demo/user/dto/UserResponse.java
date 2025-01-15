package com.example.demo.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Base DTO class for user-related responses.
 * It contains a general message describing the outcome.
 *
 * <p> The class is annotated with {@link lombok.AllArgsConstructor} and {@link lombok.Data}
 * to generate the required constructors, getters, setters, equals, hashCode, and toString methods automatically. </p>
 *
 * <p> The {@link JsonIgnore} annotation is used to ignore the `status` field during JSON serialization. </p>
 */


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {

    /**
     * A descriptive message about the outcome.
     */
    private String message;

    /**
     * The HTTP status of the response.
     * This field is ignored during JSON serialization using {@link JsonIgnore}.
     */
    @JsonIgnore
    private HttpStatus status;
}
