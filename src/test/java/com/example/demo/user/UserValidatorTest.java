package com.example.demo.user;

import com.example.demo.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    void validateUser_validUsernameAndPassword_returnsEmptyOptional() {
        String username = "validUser";
        String password = "Valid1234";

        Optional<UserResponse> result = userValidator.validateUser(username, password);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateUser_invalidUsername_returnsErrorResponse() {
        String username = "usr";
        String password = "Valid1234";

        Optional<UserResponse> result = userValidator.validateUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(HttpStatus.BAD_REQUEST, result.get().getStatus());
        assertEquals(UserMessageProvider.USERNAME_FORMAT_MESSAGE, result.get().getMessage());
    }

    @Test
    void validateUser_invalidPassword_returnsErrorResponse() {
        String username = "validUser";
        String password = "short";

        Optional<UserResponse> result = userValidator.validateUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(HttpStatus.BAD_REQUEST, result.get().getStatus());
        assertEquals(UserMessageProvider.PASSWORD_COMPLEXITY_MESSAGE, result.get().getMessage());
    }

    @Test
    void validateUser_nullPassword_returnsErrorResponse() {
        String username = "validUser";
        String password = null;

        Optional<UserResponse> result = userValidator.validateUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(HttpStatus.BAD_REQUEST, result.get().getStatus());
        assertEquals(UserMessageProvider.PASSWORD_COMPLEXITY_MESSAGE, result.get().getMessage());
    }

    @Test
    void validateUser_emptyPassword_returnsErrorResponse() {
        String username = "validUser";
        String password = "";

        Optional<UserResponse> result = userValidator.validateUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(HttpStatus.BAD_REQUEST, result.get().getStatus());
        assertEquals(UserMessageProvider.PASSWORD_COMPLEXITY_MESSAGE, result.get().getMessage());
    }
}

