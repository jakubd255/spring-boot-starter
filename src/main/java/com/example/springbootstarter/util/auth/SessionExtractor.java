package com.example.springbootstarter.util.auth;

import com.example.springbootstarter.model.Session;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionExtractor {
    private final SessionRepository sessionRepository;

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("access-token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String extractSessionToken(HttpServletRequest request) {
        String fromHeader = extractAccessTokenFromHeader(request);
        String fromCookie = extractAccessTokenFromCookie(request);

        if(fromCookie != null) {
            return fromCookie;
        }
        else {
            return fromHeader;
        }
    }

    public User extractUser(HttpServletRequest request) {
        String token = extractSessionToken(request);
        if(token == null) {
            return null;
        }
        Session session = sessionRepository.findByToken(token).orElse(null);
        if(session == null) {
            return null;
        }
        else {
            User user = session.getUser();
            user.setAuthSession(session);
            return user;
        }
    }
}