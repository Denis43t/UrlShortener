package com.example.demo.user;


import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PutMapping("/register")
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
