package com.example.demo.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * DTO (Data Transfer Object) that represents the response for user authentication.
 * It contains the authentication token, a message describing the outcome,
 * and the HTTP status of the response.
 *
 * <p> The class is annotated with {@link lombok.AllArgsConstructor} and {@link lombok.Data}
 * to generate the required constructors, getters, setters, equals, hashCode, and toString methods automatically. </p>
 *
 * <p> The {@link JsonIgnore} annotation is used to ignore the `status` field during JSON serialization. </p>
 */
@AllArgsConstructor
@Data
public class AuthUserResponse {

    /**
     * The token that is returned upon successful authentication.
     */
    private String token;

    /**
     * A descriptive message about the authentication result.
     */
    private String message;

    /**
     * The HTTP status of the authentication response.
     * This field is ignored during JSON serialization using {@link JsonIgnore}.
     */
    @JsonIgnore
    private HttpStatus status;

    public AuthUserResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Constructs a successful authentication response with a token and OK status.
     *
     * @param token the authentication token
     * @return an instance of {@link AuthUserResponse} with status {@link HttpStatus#OK}
     */
    public static AuthUserResponse success(String token) {
        return new AuthUserResponse(token, "User successfully authenticated.", HttpStatus.OK);
    }

    /**
     * Constructs a failed authentication response with a message and UNAUTHORIZED status.
     *
     * @param message the failure message
     * @return an instance of {@link AuthUserResponse} with status {@link HttpStatus#UNAUTHORIZED}
     */
    public static AuthUserResponse failed(String message) {
        return new AuthUserResponse("User is not authenticated. " + message, HttpStatus.UNAUTHORIZED);
    }
}

