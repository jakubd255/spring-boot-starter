package com.example.springbootstarter.model.dto.response;

import com.example.springbootstarter.model.type.Role;

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
