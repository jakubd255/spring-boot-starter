package com.example.springbootstarter.dto.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private final String token;
}
