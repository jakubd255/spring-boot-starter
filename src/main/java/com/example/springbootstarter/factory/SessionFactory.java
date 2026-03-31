package com.example.springbootstarter.factory;

import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {
    public Session createSession(User user) {
        return Session.builder()
                .user(user)
                .build();
    }
}
