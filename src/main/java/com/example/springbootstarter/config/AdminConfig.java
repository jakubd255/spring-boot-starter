package com.example.springbootstarter.config;

import com.example.springbootstarter.model.Role;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initialize() {
        if(userRepository.countByRole(Role.ADMIN) == 0) {
            User admin = User.builder()
                    .fullName("Admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("12345678"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Admin has been created.");
        }
    }
}
