package com.example.springbootstarter.dto.response;

import com.example.springbootstarter.model.Role;

public record UserExtendedDto(
        int id,
        String fullName,
        String email,
        Role role,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired,
        boolean isEnabled
) {}
