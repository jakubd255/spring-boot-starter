package com.example.springbootstarter.service;

import com.example.springbootstarter.factory.TokenFactory;
import com.example.springbootstarter.model.Token;
import com.example.springbootstarter.model.TokenType;
import com.example.springbootstarter.model.User;
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

    public void deleteById(Integer id) {
        tokenRepository.deleteById(id);
    }

    public void deleteAllByUserId(Integer userId) {
        tokenRepository.deleteAllByUserId(userId);
    }
}
