package com.example.springbootstarter.service;

import com.example.springbootstarter.dto.DtoConverter;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.jwt.JwtAuthenticationManager;
import com.example.springbootstarter.dto.request.LoginRequest;
import com.example.springbootstarter.dto.request.RegisterRequest;
import com.example.springbootstarter.dto.response.JwtAuthenticationResponse;
import com.example.springbootstarter.jwt.JwtGenerator;
import com.example.springbootstarter.model.Role;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final JwtAuthenticationManager authenticationManager;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    public JwtAuthenticationResponse register(RegisterRequest request) {
        try {
            User user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(user);

            String token = jwtGenerator.generateToken(user.getUsername());
            return new JwtAuthenticationResponse(token);
        }
        catch(Exception e) {
            throw new BadCredentialsException("Email is taken");
        }
    }

    public JwtAuthenticationResponse logIn(LoginRequest request) {
        //Does user exist?
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new BadCredentialsException("Invalid email")
        );

        //Is password valid?
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtGenerator.generateToken(user.getUsername());
        return new JwtAuthenticationResponse(token);
    }

    public UserDto authenticate() {
        return DtoConverter.convertUserToDto(
                authenticationManager.getAuthenticatedUser()
        );
    }
}
