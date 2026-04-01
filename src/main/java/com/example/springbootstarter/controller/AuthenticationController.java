package com.example.springbootstarter.controller;

import com.example.springbootstarter.model.dto.mapper.UserDtoMapper;
import com.example.springbootstarter.model.dto.request.*;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.util.CookieManager;
import com.example.springbootstarter.model.dto.response.UserDto;
import com.example.springbootstarter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<String> logIn(@Valid @RequestBody LoginRequest request, HttpServletResponse response, HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");

        String sessionId = authenticationService.logIn(request, userAgent);
        cookieManager.addCookie(response, sessionId);

        return ResponseEntity.ok(sessionId);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token, HttpServletResponse response, HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");

        Optional<String> sessionIdOpt = authenticationService.verifyEmail(token, userAgent);
        sessionIdOpt.ifPresent(sessionId -> cookieManager.addCookie(response, sessionId));

        return ResponseEntity.ok(sessionIdOpt.orElse(null));
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
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-email") ResponseEntity<Void> updateEmail(@Valid @RequestBody EmailRequest request) {
        authenticationService.updateEmail(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/forgot-password") ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-password") ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        authenticationService.updatePassword(request);
        return ResponseEntity.noContent().build();
    }
}
