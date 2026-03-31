package com.example.springbootstarter.service;

import com.example.springbootstarter.factory.SessionFactory;
import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionFactory sessionFactory;

    public Session create(User user) {
        Session session = sessionFactory.createSession(user);
        return sessionRepository.save(session);
    }

    public void delete(Session session) {
        sessionRepository.delete(session);
    }

    public void deleteById(Integer id) {
        sessionRepository.deleteById(id);
    }

    public void deleteAllByUserId(Integer userId) {
        sessionRepository.deleteAllByUserId(userId);
    }
}
