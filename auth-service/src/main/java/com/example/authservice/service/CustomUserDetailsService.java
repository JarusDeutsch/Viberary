package com.example.authservice.service;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.UserResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient client;

    public CustomUserDetailsService(UserServiceClient client) {
        this.client = client;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponse user = client.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        // 🟨 Додано логування
        System.out.println("✅ Отримано користувача: " + user.email());
        System.out.println("🔐 Хеш пароля: " + user.password());
        System.out.println("🎭 Роль: " + user.role());

        return new User(
                user.email(),
                user.password(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.role().toUpperCase()))
        );
    }

}
