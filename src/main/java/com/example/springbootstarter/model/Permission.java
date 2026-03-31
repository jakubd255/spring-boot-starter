package com.example.springbootstarter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_DELETE("user:delete");

    private final String permission;
}
