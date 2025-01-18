package com.example.demo.user;

import com.example.demo.security.JwtTokenProvider;
import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.util.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class responsible for handling user-related operations such as registration and authentication.
 * <p>
 * This service interacts with the {@link UserRepository} to perform CRUD operations on the `User` entity.
 * It also validates user input using the {@link UserValidator} and utilizes the {@link JwtTokenProvider} for token generation.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator validator;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user using the provided {@link UserRequest}.
     *
     * <p> Checks if the username already exists, validates the username and password formats,
     * hashes the password, and finally saves the user in the database.
     *
     * @param request The {@link UserRequest} containing the username and password.
     * @return A {@link RegisterUserResponse} indicating the success or failure of the registration.
     */
    @Override
    @Transactional
    public RegisterUserResponse registerUser(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<UserResponse> validOptional = validator.validateUser(username, password);

        if (validOptional.isPresent()) {
            UserResponse response = validOptional.get();
            return RegisterUserResponse.failed(response.getMessage(), response.getStatus());
        }


        if (userRepository.existsByUsername(username)) {
            return RegisterUserResponse.failed(MessageProvider.generateUserExistsMessage(username),
                    HttpStatus.CONFLICT);
        }


        String hashedPassword = passwordEncoder.encode(password);

        User user = userRepository.save(User.builder().username(username).password(hashedPassword).build());

        return RegisterUserResponse.success(user.getId(), username);
    }

    /**
     * Authenticates a user using the provided {@link UserRequest}.
     *
     * <p> Checks if the username exists in the database, verifies the password using the {@link PasswordEncoder},
     * and generates an authentication token using the {@link JwtTokenProvider}.
     *
     * @param request The {@link UserRequest} containing the username and password.
     * @return An {@link AuthUserResponse} indicating the success or failure of authentication.
     */
    @Override
    public AuthUserResponse authenticateUser(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<UserResponse> validOptional = validator.validateUser(username, password);

        if (validOptional.isPresent()) {
            UserResponse response = validOptional.get();
            return AuthUserResponse.failed(response.getMessage());
        }

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return AuthUserResponse.failed(MessageProvider.generateUserNotFoundMessage(username));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return AuthUserResponse.failed(MessageProvider.WRONG_PASSWORD_MESSAGE);
        }

        String token = tokenProvider.generateToken(username);
        return AuthUserResponse.success(token);
    }



}
