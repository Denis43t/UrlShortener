package com.example.demo.user;

import com.example.demo.security.TokenProvider;
import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
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
 * It also validates user input using the {@link UserValidator} and utilizes the {@link TokenProvider} for token generation.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator validator;
    private final TokenProvider tokenProvider;
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

        if (userRepository.existsByUsername(username)) {
            return RegisterUserResponse.failed(UserMessageProvider.generateUserExistsMessage(username),
                    HttpStatus.CONFLICT);
        }

        if (!validator.isValidUsernameFormat(username)) {
            return RegisterUserResponse.failed(UserMessageProvider.USERNAME_FORMAT_MESSAGE,
                    HttpStatus.BAD_REQUEST);
        }

        String password = request.getPassword();

        if (!validator.isValidPasswordFormat(password)) {
            return RegisterUserResponse.failed(UserMessageProvider.PASSWORD_COMPLEXITY_MESSAGE,
                    HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = userRepository.save(User.builder().username(username).password(hashedPassword).build());

        return RegisterUserResponse.success(user.getId(), username);
    }

    /**
     * Authenticates a user using the provided {@link UserRequest}.
     *
     * <p> Checks if the username exists in the database, verifies the password using the {@link PasswordEncoder},
     * and generates an authentication token using the {@link TokenProvider}.
     *
     * @param request The {@link UserRequest} containing the username and password.
     * @return An {@link AuthUserResponse} indicating the success or failure of authentication.
     */
    @Override
    public AuthUserResponse authenticateUser(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return AuthUserResponse.failed(UserMessageProvider.generateUserNotFoundMessage(username));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return AuthUserResponse.failed(UserMessageProvider.INVALID_PASSWORD_MESSAGE);
        }

        String token = tokenProvider.generateToken(username);
        return AuthUserResponse.success(token);
    }
}
