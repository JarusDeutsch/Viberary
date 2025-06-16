package com.example.bookingservice.util;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString("sub"); // або "email", залежно від токена
    }
}
