package com.example.springbootstarter.util;

import com.example.springbootstarter.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationManager {
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();

        if(!user.isEnabled()) {
            throw new AccessDeniedException("User is disabled");
        }

        return user;
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        else {
            return !authentication.getName().equals("anonymousUser");
        }
    }
}
