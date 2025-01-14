package com.example.demo.user;

import com.example.demo.user.dto.AuthUserResponse;
import com.example.demo.user.dto.RegisterUserResponse;
import com.example.demo.user.dto.UserRequest;

public interface UserService {
    RegisterUserResponse registerUser(UserRequest request);
    AuthUserResponse authenticateUser(UserRequest request);
}
