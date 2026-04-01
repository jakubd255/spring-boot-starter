package com.example.springbootstarter.model.factory;

import com.example.springbootstarter.model.dto.request.RegisterRequest;
import com.example.springbootstarter.model.type.Role;
import com.example.springbootstarter.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFactory {
    public final PasswordEncoder passwordEncoder;

    public  User createUser(String fullName, String email, String password) {
        return User.builder()
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
    }

    public  User createUser(RegisterRequest request) {
        return createUser(request.getFullName(), request.getEmail(), request.getPassword());
    }

    public User createAdminUser(String fullName, String email, String password) {
        return User.builder()
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();
    }
}
