package com.example.demo.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO (Data Transfer Object) that represents the user request for registration or login.
 * It contains the username and password fields needed for user authentication or registration processes.
 *
 * <p> The class is annotated with {@link lombok.AllArgsConstructor} and {@link lombok.Data}
 * to automatically generate constructors, getters, setters, equals, hashCode, and toString methods. </p>
 */
@AllArgsConstructor
@Data
public class UserRequest {

    /**
     * The username provided by the user for registration or login.
     */
    private String username;

    /**
     * The password provided by the user for registration or login.
     */
    private String password;
}

