package com.example.demo.util;

import org.springframework.stereotype.Service;

/**
 * Provides various static messages used throughout the user management functionalities.
 * This service acts as a central point to define and retrieve standardized messages
 * related to user-related operations such as validation, existence, and authentication.
 *
 * <p> It contains constants and utility methods for generating specific messages
 * used in the user registration, authentication, and validation processes.
 */
@Service
public class MessageProvider {

    /**
     * Error message indicating the username format requirement.
     * The username must be between 5 and 50 characters.
     */
    public static final String USERNAME_FORMAT_MESSAGE =
            "Username must be at least 5 and no more than 50 characters.";

    /**
     * Error message indicating the password complexity requirement.
     * The password must be at least 8 characters long and include digits,
     * uppercase, and lowercase letters.
     */
    public static final String PASSWORD_COMPLEXITY_MESSAGE =
            "Password must be at least 8 characters long, " +
                    "including digits, uppercase, and lowercase letters.";

    /**
     * Error message indicating an invalid password input.
     */
    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password. Please try again.";
    public static final String INCORRECT_URL_MESSAGE = "Url is incorrect.";
    public static final String NOT_AUTHENTICATED_MESSAGE =
            "User is not authenticated. Please login to receive the urls.";
    public static final String URL_LIST_EMPTY_MESSAGE = "URL list is empty.";
    public static final String URL_NOT_FOUND_MESSAGE = "URL could not be found.";
    public static final String INCORRECT_TOKEN_MESSAGE = "Incorrect token. Please try again.";
    public static final String EXPIRED_URL_MESSAGE = "URL has expired.";
    public static final String NOT_CORRECT_EXPIRES_AT =
            "You cannot set the expiration date to a past date. Please choose a valid future date.";

    /**
     * Generates a message indicating that a user with the specified username already exists.
     *
     * @param username The username that already exists.
     * @return A string message indicating that the user with the given name already exists.
     */
    public static String generateUserExistsMessage(String username) {
        return "User with name " + username + " already exists. Please try a different name.";
    }

    /**
     * Generates a message indicating that a user with the specified username was not found.
     *
     * @param username The username that could not be found.
     * @return A string message indicating that the user with the given name was not found.
     */
    public static String generateUserNotFoundMessage(String username) {
        return "User with name " + username + " not found.";
    }
}

