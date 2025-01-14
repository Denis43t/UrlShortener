package com.example.demo.user;

import com.example.demo.security.AuthService;
import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserService class.
 *
 * This class contains test cases to verify the behavior of the UserService,
 * including user registration and authentication functionalities.
 * The tests use Mocking for dependencies such as UserRepository, UserValidator, PasswordEncoder,
 * and AuthService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserService userService;

    /**
     * Tests the successful registration of a user.
     *
     * <p> This test verifies that when a user with a valid username and password
     * is registered, the service correctly saves the user, returns the success response,
     * and performs the necessary verifications with mocked dependencies.
     */
    @Test
    void testRegisterUser_Success() {
        UserRequest request = new UserRequest("validUsername", "ValidPassword123!");
        when(userRepository.existsByUsername("validUsername")).thenReturn(false);
        when(validator.isValidUsernameFormat("validUsername")).thenReturn(true);
        when(validator.isValidPasswordFormat("ValidPassword123!")).thenReturn(true);
        when(passwordEncoder.encode("ValidPassword123!")).thenReturn("hashedPassword");

        User savedUser = User.builder()
                .id(1L)
                .username("validUsername")
                .password("hashedPassword")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        RegisterUserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("validUsername", response.getUsername());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("User successfully registered.", response.getMessage());

        verify(userRepository).save(any(User.class));
    }

    /**
     * Tests the registration of a user with an existing username.
     *
     * <p> This test verifies that when attempting to register a user with a username
     * that already exists in the database, the service correctly returns a conflict response.
     */
    @Test
    void testRegisterUser_UsernameExists() {
        UserRequest request = new UserRequest("existingUsername", "ValidPassword123!");

        when(userRepository.existsByUsername("existingUsername")).thenReturn(true);

        RegisterUserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals("User is not registered. " +
                "User with name " + request.getUsername() + " already exists. " +
                "Please try a different name.", response.getMessage());
    }

    /**
     * Tests the successful authentication of a user.
     *
     * <p> This test verifies that when a user provides valid credentials (username and password),
     * the service correctly authenticates the user, generates a token, and returns the success response.
     */
    @Test
    void testAuthenticateUser_Success() {
        UserRequest request = new UserRequest("validUsername", "ValidPassword123!");
        User user = User.builder()
                .id(1L)
                .username("validUsername")
                .password("hashedPassword")
                .build();

        when(userRepository.findByUsername("validUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("ValidPassword123!", "hashedPassword")).thenReturn(true);
        when(authService.generateToken("validUsername")).thenReturn("generatedToken");

        AuthUserResponse response = userService.authenticateUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("generatedToken", response.getToken());
        assertEquals("User successfully authenticated.", response.getMessage());
    }

    /**
     * Tests the authentication of a user that doesn't exist.
     *
     * <p> This test verifies that when a non-existent username is provided,
     * the service correctly returns an unauthorized response.
     */
    @Test
    void testAuthenticateUser_UserNotFound() {
        UserRequest request = new UserRequest("nonExistentUser", "Password123!");
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        AuthUserResponse response = userService.authenticateUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
        assertEquals("User is not authenticated. User with name " + request.getUsername() + " not found.", response.getMessage());
    }

    /**
     * Tests the authentication of a user with an invalid password.
     *
     * <p> This test verifies that when a user provides an incorrect password,
     * the service correctly returns an unauthorized response indicating the invalid password.
     */
    @Test
    void testAuthenticateUser_InvalidPassword() {
        UserRequest request = new UserRequest("validUsername", "WrongPassword123!");
        User user = User.builder()
                .id(1L)
                .username("validUsername")
                .password("hashedPassword")
                .build();

        when(userRepository.findByUsername("validUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("WrongPassword123!", "hashedPassword")).thenReturn(false);

        AuthUserResponse response = userService.authenticateUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
        assertEquals("User is not authenticated. Invalid password. Please try again.", response.getMessage());
    }
}
