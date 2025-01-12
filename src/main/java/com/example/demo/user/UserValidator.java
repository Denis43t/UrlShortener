package com.example.demo.user;


import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserValidator {

    public boolean isValidUsernameFormat(String username) {
        return username.length() >= 5 && username.length() <= 50;
    }

    public boolean isValidPasswordFormat(String password) {
        if (Objects.isNull(password) || password.length() < 8) {
            return false;
        }

        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}
