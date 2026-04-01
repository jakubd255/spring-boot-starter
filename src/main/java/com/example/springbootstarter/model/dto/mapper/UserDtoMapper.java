package com.example.springbootstarter.model.dto.mapper;

import com.example.springbootstarter.model.dto.response.UserDto;
import com.example.springbootstarter.model.entity.User;

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
