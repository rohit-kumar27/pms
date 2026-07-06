package com.PMS.auth_service.service;

import com.PMS.auth_service.dto.LoginRequestDto;
import com.PMS.auth_service.model.User;
import com.PMS.auth_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDto loginRequest) {
        Optional<String> token =
                userService.getUserByEmail(loginRequest.getEmail())
                        .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                        .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()));

        return token;
    }

    public boolean validate(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
