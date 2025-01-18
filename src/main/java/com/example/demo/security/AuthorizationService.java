package com.example.demo.security;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;

    private final JwtTokenProvider tokenProvider;

    public Optional<User> getAuthorizedUser(String authorizationHeader) {
        String token = tokenProvider.extractTokenFromHeader(authorizationHeader);
        String username = tokenProvider.extractUsernameFromToken(token);
        return userRepository.findByUsername(username);
    }
}
