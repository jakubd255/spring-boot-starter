package com.example.springbootstarter.controller;

import com.example.springbootstarter.dto.request.LoginRequest;
import com.example.springbootstarter.dto.request.RegisterRequest;
import com.example.springbootstarter.model.User;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User user = authenticationService.register(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest) {
        User user = authenticationService.logIn(loginRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
