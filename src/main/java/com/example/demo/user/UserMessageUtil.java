package com.example.demo.user;

import org.springframework.stereotype.Service;

@Service
public class UserMessageUtil {
    public static final String USERNAME_FORMAT_MESSAGE =
            "Username must be at least 5 and no more than 50 characters";

    public static final String PASSWORD_COMPLEXITY_MESSAGE =
            "Password must be at least 8 characters long, " +
                    "including digits, uppercase, and lowercase letters";

    public static final String INVALID_PASSWORD_MESSAGE = "Invalid password. Please try again.";

    public String generateUserExistsMessage(String username) {
        return "User with name + " + username + " already exists. Please try a different name.";
    }

    public String generateUserNotFoundMessage(String username) {
        return "User with name " + username + " not found.";
    }
}
