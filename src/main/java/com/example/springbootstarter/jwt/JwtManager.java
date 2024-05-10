package com.example.springbootstarter.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Component
public class JwtManager {
    @Value("${token.secret.key}")
    private String jwtSecretKey;

    @Value("${token.expiration.time}")
    private int jwtExpirationTime;

    public String generateToken(String username) {
        Date currectDate = new Date();
        Date expirationDate = new Date(currectDate.getTime()+jwtExpirationTime);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder
                .setIssuedAt(currectDate)
                .setExpiration(expirationDate)
                .setClaims(new HashMap<>())
                .setSubject(username)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
