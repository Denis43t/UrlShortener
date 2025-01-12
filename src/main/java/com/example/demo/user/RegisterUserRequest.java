package com.example.demo.user;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserRequest {
    private String username;
    private String password;
}
