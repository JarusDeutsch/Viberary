package com.example.userservice.dto;

import com.example.userservice.model.User.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
