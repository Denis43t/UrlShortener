package com.example.demo.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * DTO (Data Transfer Object) that represents the response for user registration.
 * It contains the user ID, username, a message describing the outcome,
 * and the HTTP status of the response.
 *
 * <p> The class is annotated with {@link lombok.AllArgsConstructor} and {@link lombok.Data}
 * to generate the required constructors, getters, setters, equals, hashCode, and toString methods automatically. </p>
 *
 * <p> The {@link JsonIgnore} annotation is used to ignore the `status` field during JSON serialization. </p>
 */
@AllArgsConstructor
@Data
public class RegisterUserResponse {

    /**
     * The ID of the registered user.
     */
    private Long userId;

    /**
     * The username of the registered user.
     */
    private String username;

    /**
     * A descriptive message about the registration result.
     */
    private String message;

    /**
     * The HTTP status of the registration response.
     * This field is ignored during JSON serialization using {@link JsonIgnore}.
     */
    @JsonIgnore
    private HttpStatus status;

    public RegisterUserResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Constructs a successful registration response with user ID, username, and CREATED status.
     *
     * @param id the user ID
     * @param username the registered username
     * @return an instance of {@link RegisterUserResponse} with status {@link HttpStatus#CREATED}
     */
    public static RegisterUserResponse success(Long id, String username) {
        return new RegisterUserResponse(id, username, "User successfully registered.", HttpStatus.CREATED);
    }

    /**
     * Constructs a failed registration response with a message and specific status.
     *
     * @param message the failure message
     * @param status the HTTP status
     * @return an instance of {@link RegisterUserResponse} with the provided message and status
     */
    public static RegisterUserResponse failed(String message, HttpStatus status) {
        return new RegisterUserResponse("User is not registered. " + message, status);
    }
}

