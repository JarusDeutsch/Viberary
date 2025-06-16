package com.example.bookingservice.client;

import com.example.bookingservice.dto.UserResponse;
import com.example.bookingservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {

    private final WebClient webClient;
    private final TokenService tokenService;

    public UserResponse getUserByEmail(String email) {
        String token = tokenService.getAccessToken(); // 🔑 Отримуємо токен

        try {
            return webClient
                    .get()
                    .uri("http://user-service:8081/api/users/by-email?email={email}", email)
                    .headers(headers -> headers.setBearerAuth(token)) // ✅ Додаємо токен
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("❌ Помилка запиту до user-service: статус={}, причина={}, тіло={}",
                    e.getStatusCode(), e.getMessage(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("❌ Несподівана помилка при виклику user-service", e);
            return null;
        }
    }
}
