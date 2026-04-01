package com.example.springbootstarter.service;

import com.example.springbootstarter.model.factory.SessionFactory;
import com.example.springbootstarter.model.entity.Session;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.repository.SessionRepository;
import com.example.springbootstarter.util.agent.ParsedUserAgent;
import com.example.springbootstarter.util.agent.UserAgentParser;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionFactory sessionFactory;
    private final UserAgentParser userAgentParser;

    public Session create(User user, String userAgent) {
        ParsedUserAgent parsed = userAgentParser.parse(userAgent);
        String os = parsed.getOperatingSystem();
        String browser = parsed.getBrowser();

        Session session = sessionFactory.createSession(user, os, browser);
        return sessionRepository.save(session);
    }

    public Session getById(Integer id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public List<Session> getAll() {
        return sessionRepository.findAll();
    }

    public List<Session> getByUserId(Integer userId) {
        return sessionRepository.findByUserId(userId);
    }

    public void delete(Session session) {
        sessionRepository.delete(session);
    }

    public void deleteByUserId(Integer userId) {
        sessionRepository.deleteByUserId(userId);
    }
}
