package com.example.springbootstarter.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtExtractor {
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

    default String extractJwt(HttpServletRequest request) {
        String jwtHeader = extractAccessTokenFromHeader(request);
        String jwtCookie = extractAccessTokenFromCookie(request);

        if(jwtCookie != null) {
            return jwtCookie;
        }
        else {
            return jwtHeader;
        }
    }
}
