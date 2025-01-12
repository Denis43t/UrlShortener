package com.example.demo.user;


import com.example.demo.security.AuthService;
import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator validator;
    private final UserMessageUtil messageUtil;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;


    public RegisterUserResponse registerUser(UserRequest request) {
        String username = request.getUsername();

        if (!userRepository.existsByUsername(username)) {
            return RegisterUserResponse.failed(messageUtil.generateUserExistsMessage(username),
                    HttpStatus.CONFLICT);
        }

        if (!validator.isValidUsernameFormat(username)) {
            return RegisterUserResponse.failed(UserMessageUtil.USERNAME_FORMAT_MESSAGE,
                    HttpStatus.BAD_REQUEST);
        }

        String password = request.getPassword();

        if (!validator.isValidPasswordFormat(password)) {
            return RegisterUserResponse.failed(UserMessageUtil.PASSWORD_COMPLEXITY_MESSAGE,
                    HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = userRepository.save(User.builder().username(username).password(hashedPassword).build());

        return RegisterUserResponse.success(user.getId(), username);
    }


    public AuthUserResponse authenticateUser(UserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return AuthUserResponse.failed(messageUtil.generateUserNotFoundMessage(username));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return AuthUserResponse.failed(UserMessageUtil.INVALID_PASSWORD_MESSAGE);
        }

        String token = authService.generateToken(username);
        return AuthUserResponse.success(token);
    }

}
