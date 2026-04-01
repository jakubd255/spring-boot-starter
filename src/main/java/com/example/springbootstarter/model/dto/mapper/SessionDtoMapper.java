package com.example.springbootstarter.model.dto.mapper;

import com.example.springbootstarter.model.dto.response.SessionDto;
import com.example.springbootstarter.model.entity.Session;

import java.util.List;

public class SessionDtoMapper {
    public static SessionDto mapSessionToDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getBrowser(),
                session.getOs(),
                session.getCreatedAt(),
                session.getExpiresAt()
        );
    }

    public static List<SessionDto> mapSessionsToDto(List<Session> sessions) {
        return sessions.stream().map(SessionDtoMapper::mapSessionToDto).toList();
    }
}
