package com.example.bookingservice.client;

import com.example.bookingservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user-service.url:http://user-service:8081}")
    private String userServiceUrl;

    @Value("${auth.internal-token:}")
    private String internalToken;

    public UserResponse getUserByEmail(String email) {
        String url = userServiceUrl + "/api/users/by-email?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(internalToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, UserResponse.class
        );

        return response.getBody();
    }
}
