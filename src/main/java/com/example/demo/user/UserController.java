package com.example.demo.user;


import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody UserRequest request) {
        RegisterUserResponse response = userService.registerUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserResponse> authenticateUser(@RequestBody UserRequest request) {
        AuthUserResponse response = userService.authenticateUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
