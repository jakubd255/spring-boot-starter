package com.example.springbootstarter.factory;

import com.example.springbootstarter.model.Token;
import com.example.springbootstarter.model.TokenType;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.util.TokenCodeGenerator;

public class TokenFactory {
    public static Token createEmailToken(User user) {
        return Token.builder()
                .user(user)
                .code(TokenCodeGenerator.generateTokenCode())
                .type(TokenType.EMAIL_VERIFICATION)
                .build();
    }
}
