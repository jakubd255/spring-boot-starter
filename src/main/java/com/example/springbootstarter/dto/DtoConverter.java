package com.example.springbootstarter.dto;

import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.model.User;

public class DtoConverter {
    public static UserDto convertUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPendingEmail(),
                user.getRole(),
                user.isVerified(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}
