package com.example.springbootstarter;

import com.example.springbootstarter.jwt.JwtManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtManagerTest {
    private JwtManager jwtManager;

    @BeforeEach
    public void setUp() {
        final String jwtSecretKey = "Ph4ZEGgDfsTDgVkJPuwGoy4A5gfdnMoTkshMp53iqfIhdtqh9t4";
        final int jwtExpirationTime = 10000;

        jwtManager = new JwtManager();
        ReflectionTestUtils.setField(jwtManager, "jwtSecretKey", jwtSecretKey);
        ReflectionTestUtils.setField(jwtManager, "jwtExpirationTime", jwtExpirationTime);
    }

    @Test
    public void testCreateTokenAndRead() {
        String testEmail = "test@gmail.com";
        String token = jwtManager.generateToken(testEmail);

        String emailFromToken = jwtManager.extractUsername(token);

        Assertions.assertEquals(testEmail, emailFromToken);
        Assertions.assertTrue(jwtManager.isTokenValid(token, testEmail));
    }
}
