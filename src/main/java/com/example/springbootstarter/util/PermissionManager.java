package com.example.springbootstarter.util;

import com.example.springbootstarter.model.type.Permission;
import com.example.springbootstarter.model.entity.Session;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.util.auth.UserAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PermissionManager {
    private final UserAuthenticationManager authenticationManager;

    private boolean hasPermission(Permission permission) {
        User current = authenticationManager.getAuthenticatedUser();
        return current.getRole().getPermissions().contains(permission);
    }

    private boolean isOwner(Integer userId) {
        User authUser = authenticationManager.getAuthenticatedUser();
        return authUser.getId().equals(userId);
    }

    private boolean isOwner(User user) {
        return isOwner(user.getId());
    }

    public void checkCanReadUser(User user) {
        if(hasPermission(Permission.USER_READ) || isOwner(user)) return;
        throw new AccessDeniedException("You don't have permission to read this user");
    }

    public void checkCanUpdateUser(User user) {
        if(hasPermission(Permission.USER_UPDATE) || isOwner(user)) return;
        throw new AccessDeniedException("You don't have permission to update this user");
    }

    public void checkCanDeleteUser(User user) {
        if(hasPermission(Permission.USER_DELETE) || isOwner(user)) return;
        throw new AccessDeniedException("You don't have permission to delete this user");
    }

    public void checkCanReadSessions(Integer userId) {
        if(hasPermission(Permission.SESSION_READ) || isOwner(userId)) return;
        throw new AccessDeniedException("You don't have permission to read these sessions");
    }

    public void checkCanDeleteSessions() {
        if(hasPermission(Permission.SESSION_DELETE)) return;
        throw new AccessDeniedException("You do not have permission to delete these sessions");
    }

    public void checkCanDeleteSession(Session session) {
        if(hasPermission(Permission.SESSION_DELETE) || isOwner(session.getUser())) return;
        throw new AccessDeniedException("You do not have permission to delete this session");
    }
}
