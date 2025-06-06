package com.example.authservice.controller;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        String encoded = passwordEncoder.encode(request.password());
        RegisterRequest secureRequest = new RegisterRequest(request.name(), request.email(), encoded);
        UserResponse created = userServiceClient.createUser(secureRequest);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        UserResponse user = userServiceClient.getUserByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Невірний пароль");
        }
        return ResponseEntity.ok("Аутентифікація успішна");
    }
}
