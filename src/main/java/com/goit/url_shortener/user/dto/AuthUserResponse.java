package com.goit.url_shortener.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * DTO (Data Transfer Object) that represents the response for user authentication.
 * It contains the authentication token, a message describing the outcome,
 * and the HTTP status of the response.
 *
 * The class is annotated with {@link lombok.Data} and {@link lombok.EqualsAndHashCode}
 *  * to generate the required constructors, getters, setters, equals, hashCode, and toString methods automatically. </p>
 * <p> The {@link JsonIgnore} annotation is used to ignore the `status` field during JSON serialization. </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserResponse extends UserResponse {

    /**
     * The token that is returned upon successful authentication.
     */
    private String token;


    public AuthUserResponse(String message, HttpStatus status) {
        super(message, status);
    }

    public AuthUserResponse(String token, String message, HttpStatus status) {
        super(message, status);
        this.token = token;
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

