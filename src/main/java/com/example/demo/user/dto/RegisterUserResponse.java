package com.example.demo.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class RegisterUserResponse {
    private Long id;
    private String username;
    private String message;

    @JsonIgnore
    private HttpStatus status;

    public RegisterUserResponse(String message, HttpStatus status) {
        this.message = message;
    }

    public static RegisterUserResponse success(Long id, String username) {
        return new RegisterUserResponse(id, username, "User successfully registered", HttpStatus.CREATED);
    }

    public static RegisterUserResponse failed(String message, HttpStatus status) {
        return new RegisterUserResponse("User is not registered. " + message, status);
    }

}
