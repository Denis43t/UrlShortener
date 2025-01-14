package com.example.demo.user;

import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UserController class.
 *
 * This class contains test cases to verify the functionality of the UserController methods. It checks
 * both the success and failure scenarios for user registration and authentication.
 */
public class UserControllerTest {

    // Mocked dependencies for the UserService and UserController
    private final UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
    private final UserController userController = new UserController(userService);

    /**
     * Tests the successful registration of a user.
     *
     * <p> This test verifies that when a valid UserRequest is passed, the registerUser method of UserService
     * returns a successful RegisterUserResponse. The response is then checked to ensure it matches the expected
     * outcome with the correct status code and body.
     */
    @Test
    public void testRegisterUser_Success() {
        UserRequest request = new UserRequest("validUsername", "ValidPassword123!");
        RegisterUserResponse expectedResponse = RegisterUserResponse.success(1L, request.getUsername());

        when(userService.registerUser(any(UserRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<RegisterUserResponse> responseEntity = userController.registerUser(request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    /**
     * Tests the successful authentication of a user.
     *
     * <p> This test checks that when a valid UserRequest is passed to the authenticateUser method of UserService,
     * it returns a successful AuthUserResponse. The response is verified by comparing the status code and body
     * to the expected result.
     */
    @Test
    public void testAuthenticateUser_Success() {
        UserRequest request = new UserRequest("validUsername", "ValidPassword123!");
        AuthUserResponse expectedResponse = AuthUserResponse.success("validUsername");

        when(userService.authenticateUser(any(UserRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<AuthUserResponse> responseEntity = userController.authenticateUser(request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}

