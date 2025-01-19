package com.goit.url_shortener.user;

import com.goit.url_shortener.user.dto.AuthUserResponse;
import com.goit.url_shortener.user.dto.RegisterUserResponse;
import com.goit.url_shortener.user.dto.UserRequest;

public interface UserService {
    RegisterUserResponse registerUser(UserRequest request);
    AuthUserResponse authenticateUser(UserRequest request);
}
