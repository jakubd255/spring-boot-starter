package com.example.springbootstarter.dto.converter;

import com.example.springbootstarter.dto.response.SessionDto;
import com.example.springbootstarter.model.Session;

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
