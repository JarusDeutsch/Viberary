package com.example.authservice.client;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;

    public UserServiceClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public UserResponse createUser(RegisterRequest request) {
        return restTemplate.postForObject("http://user-service:8081/api/users", request, UserResponse.class);
    }

    public UserResponse getUserByEmail(String email) {
        String url = "http://user-service:8081/api/users/by-email?email=" + email;
        return restTemplate.getForObject(url, UserResponse.class);
    }

    public Optional<User> loadUserByEmail(String email) {
        try {
            UserResponse response = getUserByEmail(email);
            return Optional.of(new User(
                    response.email(),
                    response.password(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + response.role()))
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
