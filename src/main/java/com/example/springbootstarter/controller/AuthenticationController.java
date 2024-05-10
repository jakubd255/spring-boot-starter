package com.example.springbootstarter.controller;

import com.example.springbootstarter.dto.request.LoginRequest;
import com.example.springbootstarter.dto.request.RegisterRequest;
import com.example.springbootstarter.dto.response.JwtAuthenticationResponse;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        JwtAuthenticationResponse token = authenticationService.register(registerRequest);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse token = authenticationService.logIn(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate() {
        return new ResponseEntity<>(authenticationService.authenticate(), HttpStatus.OK);
    }
}
