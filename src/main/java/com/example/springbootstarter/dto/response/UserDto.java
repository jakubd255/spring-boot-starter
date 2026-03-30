package com.example.springbootstarter.dto.response;

import com.example.springbootstarter.model.Role;

import java.time.LocalDateTime;

public record UserDto(
        int id,
        String fullName,
        String email,
        String pendingEmail,
        Role role,
        boolean verified,
        boolean active,
        LocalDateTime createdAt
) {}
