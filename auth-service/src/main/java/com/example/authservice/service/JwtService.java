package com.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public String generateToken(String subject, String role) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://auth-service:9000")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject(subject)
                .claim("scope", "ROLE_" + role.toUpperCase())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateInternalToken() {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://auth-service:9000")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject("internal-service")
                .claim("scope", "write")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
