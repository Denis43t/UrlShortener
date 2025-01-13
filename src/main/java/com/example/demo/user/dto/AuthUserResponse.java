package com.example.demo.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class AuthUserResponse {
    private String token;
    private String message;

    @JsonIgnore
    private HttpStatus status;

    public AuthUserResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static AuthUserResponse success(String token) {
        return new AuthUserResponse(token, "User successfully authenticated.", HttpStatus.OK);
    }

    public static AuthUserResponse failed(String message) {
        return new AuthUserResponse("User is not authenticated. " + message, HttpStatus.UNAUTHORIZED);
    }


}
