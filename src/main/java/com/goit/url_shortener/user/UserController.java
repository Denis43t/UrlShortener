package com.goit.url_shortener.user;


import com.goit.url_shortener.user.dto.AuthUserResponse;
import com.goit.url_shortener.user.dto.RegisterUserResponse;
import com.goit.url_shortener.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for handling user authentication and registration requests.
 * This controller exposes endpoints for registering and authenticating users.
 * It communicates with the {@link UserServiceImpl} to perform the business logic.
 *
 * <p> The controller provides two main endpoints:
 * <ul>
 *   <li>{@code POST /api/v1/auth/register}: Handles user registration.
 *       Accepts a {@link UserRequest} containing user credentials and returns a {@link RegisterUserResponse}.</li>
 *   <li>{@code POST /api/v1/auth/login}: Handles user authentication.
 *       Accepts a {@link UserRequest} containing user credentials and returns an {@link AuthUserResponse}.</li>
 * </ul>
 *
 * <p> This controller uses Spring's {@link ResponseEntity} to provide standardized HTTP responses.
 */
@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    /**
     * Endpoint to handle user registration.
     *
     * @param request - The {@link UserRequest} object containing user credentials (username and password).
     * @return A {@link ResponseEntity} containing the {@link RegisterUserResponse} with the registration status.
     */
    @PostMapping(path = {"/register", "/register/"})
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody UserRequest request) {
        RegisterUserResponse response = userService.registerUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Endpoint to handle user authentication.
     *
     * @param request - The {@link UserRequest} object containing user credentials (username and password).
     * @return A {@link ResponseEntity} containing the {@link AuthUserResponse} with the authentication status.
     */
    @PostMapping(path = {"/login", "/login/"})
    @Cacheable(value = "authCache", key = "#request.username")
    public ResponseEntity<AuthUserResponse> authenticateUser(@RequestBody UserRequest request) {
        AuthUserResponse response = userService.authenticateUser(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + response.getToken());
        return ResponseEntity
                .status(response.getStatus())
                .headers(headers)
                .body(response);
    }
}
