package com.example.springbootstarter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    SESSION_READ("session:read"),
    SESSION_DELETE("session:delete");

    private final String permission;
}
