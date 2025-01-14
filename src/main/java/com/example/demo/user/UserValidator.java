package com.example.demo.user;

import org.springframework.stereotype.Service;

import java.util.Objects;

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
     * Checks if the provided username meets the required format.
     *
     * <p> Validates that the username length is between {@value MIN_USERNAME_LENGTH} and {@value MAX_USERNAME_LENGTH}.
     *
     * @param username The username to be validated.
     * @return {@code true} if the username format is valid, otherwise {@code false}.
     */
    public boolean isValidUsernameFormat(String username) {
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
    public boolean isValidPasswordFormat(String password) {
        if (Objects.isNull(password) || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}

