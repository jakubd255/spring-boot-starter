package com.example.springbootstarter.controller;

import com.example.springbootstarter.cookie.CookieManager;
import com.example.springbootstarter.dto.request.LoginRequest;
import com.example.springbootstarter.dto.request.RegisterRequest;
import com.example.springbootstarter.dto.response.JwtResponse;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CookieManager cookieManager;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        JwtResponse token = authenticationService.register(request);
        cookieManager.addCookie(response, token.token());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<JwtResponse> logIn(@RequestBody LoginRequest request, HttpServletResponse response) {
        JwtResponse token = authenticationService.logIn(request);
        cookieManager.addCookie(response, token.token());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate() {
        return new ResponseEntity<>(authenticationService.authenticate(), HttpStatus.OK);
    }

    @GetMapping("/log-out")
    public ResponseEntity<String> logOut(HttpServletResponse response) {
        cookieManager.removeCookies(response);
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }
}
