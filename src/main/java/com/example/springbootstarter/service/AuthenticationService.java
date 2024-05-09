package com.example.springbootstarter.service;

import com.example.springbootstarter.dto.request.LoginRequest;
import com.example.springbootstarter.dto.request.RegisterRequest;
import com.example.springbootstarter.model.Role;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        try {
            User user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            return userRepository.save(user);
        }
        catch(Exception e) {
            throw new BadCredentialsException("Email is taken");
        }
    }

    public User logIn(LoginRequest request) {
        //Does user exist?
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new BadCredentialsException("Invalid email")
        );

        //Is password valid?
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return user;
    }
}
