package com.example.springbootstarter.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String field;
    private String message;

    public ErrorResponse(String message) {
        this.field = null;
        this.message = message;
    }
}
