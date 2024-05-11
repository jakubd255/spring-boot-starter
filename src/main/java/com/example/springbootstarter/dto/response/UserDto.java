package com.example.springbootstarter.dto.response;

import com.example.springbootstarter.model.Role;

public record UserDto(
        int id,
        String fullName,
        String email,
        Role role
) {}
