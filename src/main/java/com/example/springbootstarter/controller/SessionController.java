package com.example.springbootstarter.controller;

import com.example.springbootstarter.model.dto.mapper.SessionDtoMapper;
import com.example.springbootstarter.model.dto.response.SessionDto;
import com.example.springbootstarter.model.entity.Session;
import com.example.springbootstarter.service.SessionService;
import com.example.springbootstarter.util.PermissionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final PermissionManager permissionManager;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SessionDto>> getByUserId(@PathVariable Integer userId) {
        permissionManager.checkCanReadSessions(userId);
        List<Session> sessions = sessionService.getByUserId(userId);
        return ResponseEntity.ok(SessionDtoMapper.mapSessionsToDto(sessions));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Session session = sessionService.getById(id);
        permissionManager.checkCanDeleteSession(session);
        sessionService.delete(session);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('session:delete')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Integer userId) {
        permissionManager.checkCanDeleteSessions();
        sessionService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
