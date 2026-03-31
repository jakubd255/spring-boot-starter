package com.example.springbootstarter.util;

import com.example.springbootstarter.model.Permission;
import com.example.springbootstarter.model.User;
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

    private boolean isOwner(User user) {
        User authUser = authenticationManager.getAuthenticatedUser();
        return authUser.getId().equals(user.getId());
    }

    public void checkCanReadUser(User user) {
        if(hasPermission(Permission.USER_READ) || isOwner(user)) return;
        throw new AccessDeniedException("You do not have permission to read this user");
    }

    public void checkCanUpdateUser(User user) {
        if(hasPermission(Permission.USER_UPDATE) || isOwner(user)) return;
        throw new AccessDeniedException("You do not have permission to update this user");
    }

    public void checkCanDeleteUser(User user) {
        if(hasPermission(Permission.USER_DELETE) || isOwner(user)) return;
        throw new AccessDeniedException("Not allowed to delete this user");
    }
}
