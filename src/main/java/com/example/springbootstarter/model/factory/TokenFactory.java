package com.example.springbootstarter.model.factory;

import com.example.springbootstarter.model.entity.Token;
import com.example.springbootstarter.model.type.TokenType;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.util.TokenCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {
    public Token createEmailToken(User user) {
        return Token.builder()
                .user(user)
                .code(TokenCodeGenerator.generateTokenCode())
                .type(TokenType.EMAIL_VERIFICATION)
                .build();
    }

    public Token createPasswordToken(User user) {
        return Token.builder()
                .user(user)
                .code(TokenCodeGenerator.generateTokenCode())
                .type(TokenType.RESET_PASSWORD)
                .build();
    }
}
