package com.example.authservice.client;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserRequest;
import com.example.authservice.dto.UserRequest.Role;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserResponse createUser(RegisterRequest request) {
        String token = jwtService.generateInternalToken();

        UserRequest userRequest = new UserRequest(
                request.name(),
                request.email(),
                request.password(),
                Role.USER
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<UserResponse> response = restTemplate.postForEntity(
                userServiceUrl + "/api/users",
                entity,
                UserResponse.class
        );

        return response.getBody();
    }

    public UserResponse getUserByEmail(String email) {
        String token = jwtService.generateInternalToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // ❌ НЕ кодуємо email
        ResponseEntity<UserResponse> response = restTemplate.exchange(
                userServiceUrl + "/api/users/by-email?email=" + email,
                HttpMethod.GET,
                entity,
                UserResponse.class
        );


        return response.getBody();
    }

}
