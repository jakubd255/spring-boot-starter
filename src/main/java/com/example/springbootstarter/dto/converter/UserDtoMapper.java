package com.example.springbootstarter.dto.converter;

import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.model.User;

import java.util.List;

public class UserDtoMapper {
    public static UserDto mapUserToDto(User user) {
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

    public static List<UserDto> mapUsersToDto(List<User> users) {
        return users.stream().map(UserDtoMapper::mapUserToDto).toList();
    }
}
