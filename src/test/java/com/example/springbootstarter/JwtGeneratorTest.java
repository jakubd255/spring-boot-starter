package com.example.springbootstarter;

import com.example.springbootstarter.jwt.JwtGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtGeneratorTest {
    private JwtGenerator jwtGenerator;

    @BeforeEach
    public void setUp() {
        final String jwtSecretKey = "33LqCZ1O+NTf26VGiXa4yy/UIZBizjSA8GK4AxdCN5U=";
        final int jwtExpirationTime = 10000;

        jwtGenerator = new JwtGenerator();
        ReflectionTestUtils.setField(jwtGenerator, "jwtSecretKey", jwtSecretKey);
        ReflectionTestUtils.setField(jwtGenerator, "jwtExpirationTime", jwtExpirationTime);
    }

    @Test
    public void testCreateTokenAndRead() {
        String testEmail = "test@gmail.com";
        String token = jwtGenerator.generateToken(testEmail);

        String emailFromToken = jwtGenerator.extractUsername(token);

        Assertions.assertEquals(testEmail, emailFromToken);
        Assertions.assertTrue(jwtGenerator.isTokenValid(token, testEmail));
    }
}
