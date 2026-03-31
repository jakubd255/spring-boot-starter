package com.example.springbootstarter.factory;

import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {
    public Session createSession(User user, String os, String browser) {
        return Session.builder()
                .user(user)
                .os(os)
                .browser(browser)
                .build();
    }
}
