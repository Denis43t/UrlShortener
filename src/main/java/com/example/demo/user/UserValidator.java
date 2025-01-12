package com.example.demo.user;


import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserValidator {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 50;

    public boolean isValidUsernameFormat(String username) {
        return username.length() >= MIN_USERNAME_LENGTH && username.length() <= MAX_USERNAME_LENGTH;
    }

    public boolean isValidPasswordFormat(String password) {
        if (Objects.isNull(password) || password.length() < 8) {
            return false;
        }

        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}
