package com.example.springbootstarter.controller;

import com.example.springbootstarter.dto.converter.UserDtoMapper;
import com.example.springbootstarter.dto.request.*;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.util.CookieManager;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CookieManager cookieManager;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        String sessionId = authenticationService.logIn(request);
        cookieManager.addCookie(response, sessionId);
        return ResponseEntity.ok(sessionId);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        String sessionId = authenticationService.verifyEmail(token);
        return ResponseEntity.ok(sessionId);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate() {
        User user = authenticationService.authenticate();
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(user));
    }

    @GetMapping("/log-out")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        authenticationService.logOut();
        cookieManager.removeCookies(response);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/update-email") ResponseEntity<Void> updateEmail(@Valid @RequestBody EmailRequest request) {
        authenticationService.updateEmail(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/forgot-password") ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/update-password") ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        authenticationService.updatePassword(request);
        return ResponseEntity.ok(null);
    }
}
