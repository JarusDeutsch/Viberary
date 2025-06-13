package com.example.authservice.controller;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Хешуємо пароль
            String encodedPassword = passwordEncoder.encode(request.password());
            RegisterRequest secureRequest = new RegisterRequest(
                    request.name(),
                    request.email(),
                    encodedPassword
            );

            // Створюємо користувача через user-service
            UserResponse user = userServiceClient.createUser(secureRequest);

            // Генеруємо токен одразу після реєстрації
            String token = jwtService.generateToken(user.email(), user.role());

            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Помилка реєстрації: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Аутентифікація
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            // Отримання користувача
            UserResponse user = userServiceClient.getUserByEmail(request.email());

            // Генерація токена
            String token = jwtService.generateToken(user.email(), user.role());

            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Невірні дані");
        }
    }
}
