package com.example.springbootstarter.factory;

import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
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
