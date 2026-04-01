package com.example.springbootstarter.model.factory;

import com.example.springbootstarter.model.entity.Session;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.util.TokenCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {
    public Session createSession(User user, String os, String browser) {
        return Session.builder()
                .user(user)
                .token(TokenCodeGenerator.generateTokenCodeUUID())
                .os(os)
                .browser(browser)
                .build();
    }
}
