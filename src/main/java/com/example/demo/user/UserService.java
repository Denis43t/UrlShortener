package com.example.demo.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;

    private static final String USERNAME_FORMAT_MESSAGE =
            "Username must be at least 5 and no more than 50 characters";

    private static final String PASSWORD_COMPLEXITY_MESSAGE =
            "Password must be at least 8 characters long, " +
                    "including digits, uppercase, and lowercase letters";


    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        String username = request.getUsername();

        if (userRepository.existsByName(username)) {
            return RegisterUserResponse.failed(generateUserExistsMessage(username), HttpStatus.CONFLICT);
        }

        if (!validator.isValidUsernameFormat(username)) {
            return RegisterUserResponse.failed(USERNAME_FORMAT_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        String password = request.getPassword();

        if (!validator.isValidPasswordFormat(password)) {
            return RegisterUserResponse.failed(PASSWORD_COMPLEXITY_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = userRepository.save(User.builder().username(username).password(hashedPassword).build());

        return RegisterUserResponse.success(user.getId(), username);
    }



    private String generateUserExistsMessage(String username) {
        return "User with name + " + username + " already exists. Please try a different name.";
    }
}
