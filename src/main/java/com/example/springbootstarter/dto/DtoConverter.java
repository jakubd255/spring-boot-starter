package com.example.springbootstarter.dto;

import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.dto.response.UserExtendedDto;
import com.example.springbootstarter.model.User;

public class DtoConverter {
    public static UserDto convertUserToDto(User user) {
        return new UserDto(
                user.getId(), user.getFullName(), user.getEmail(), user.getRole()
        );
    }

    public static UserExtendedDto convertUserToExtendedDto(User user) {
        return new UserExtendedDto(
                user.getId(), user.getFullName(), user.getEmail(), user.getRole(),
                user.isAccountNonExpired(), user.isAccountNonLocked(),
                user.isCredentialsNonExpired(), user.isEnabled()
        );
    }
}
