package com.PMS.auth_service.dto;

public class LoginResponseDto {
    public LoginResponseDto(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }
}
