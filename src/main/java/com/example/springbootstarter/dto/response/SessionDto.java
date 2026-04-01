package com.example.springbootstarter.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public record SessionDto(
        Integer id,
        String browser,
        String os,
        LocalDateTime createdAt,
        Date expiresAt
) {
}
