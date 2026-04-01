package com.example.springbootstarter.service;

import com.example.springbootstarter.model.factory.TokenFactory;
import com.example.springbootstarter.model.entity.Token;
import com.example.springbootstarter.model.type.TokenType;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.repository.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenFactory tokenFactory;

    public Token createEmailToken(User user) {
        Token token = tokenFactory.createEmailToken(user);
        return tokenRepository.save(token);
    }

    public Token createPasswordToken(User user) {
        Token token = tokenFactory.createPasswordToken(user);
        return tokenRepository.save(token);
    }

    public Token getValidToken(String code, TokenType type) {
        Token token = tokenRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));

        if(token.getExpiresAt().before(new Date())) {
            throw new BadCredentialsException("Token expired");
        }
        if(token.getType() != type) {
            throw new BadCredentialsException("Invalid token type");
        }

        return token;
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    public void deleteByUserId(Integer userId) {
        tokenRepository.deleteByUserId(userId);
    }
}
