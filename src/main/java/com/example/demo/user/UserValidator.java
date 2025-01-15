package com.example.demo.user;

import com.example.demo.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Validator class responsible for validating user-related input such as usernames and passwords.
 *
 * This service contains methods that check if a username or password meets the required format constraints.
 * It is used throughout the application to ensure the correctness and security of user input.
 */
@Service
public class UserValidator {

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Validates the username and password format.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return An `Optional` with the `ResponseEntity` if validation fails, or empty if successful.
     */
    public Optional<UserResponse> validateUser(String username, String password) {
        if (!isValidUsernameFormat(username)) {
            return Optional.ofNullable(UserResponse.builder()
                    .message(UserMessageProvider.USERNAME_FORMAT_MESSAGE)
                    .status(HttpStatus.BAD_REQUEST).build());
        }

        if (!isValidPasswordFormat(password)) {
            return Optional.ofNullable(UserResponse.builder()
                    .message(UserMessageProvider.PASSWORD_COMPLEXITY_MESSAGE)
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }

        return Optional.empty();
    }




    /**
     * Checks if the provided username meets the required format.
     *
     * <p> Validates that the username length is between {@value MIN_USERNAME_LENGTH} and {@value MAX_USERNAME_LENGTH}.
     *
     * @param username The username to be validated.
     * @return {@code true} if the username format is valid, otherwise {@code false}.
     */
    private boolean isValidUsernameFormat(String username) {
        return username.length() >= MIN_USERNAME_LENGTH && username.length() <= MAX_USERNAME_LENGTH;
    }

    /**
     * Checks if the provided password meets the required format.
     *
     * <p> Validates that the password has a minimum length of {@value MIN_PASSWORD_LENGTH} and contains at least
     * one lowercase letter, one uppercase letter, and one digit.
     *
     * @param password The password to be validated.
     * @return {@code true} if the password format is valid, otherwise {@code false}.
     */
    private boolean isValidPasswordFormat(String password) {
        if (Objects.isNull(password) || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}

