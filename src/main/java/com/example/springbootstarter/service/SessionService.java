package com.example.springbootstarter.service;

import com.example.springbootstarter.factory.SessionFactory;
import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.SessionRepository;
import com.example.springbootstarter.util.agent.ParsedUserAgent;
import com.example.springbootstarter.util.agent.UserAgentParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


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

    public void delete(Session session) {
        sessionRepository.delete(session);
    }

    public void deleteAllByUserId(Integer userId) {
        sessionRepository.deleteAllByUserId(userId);
    }
}
