package com.example.springbootstarter;

import com.example.springbootstarter.util.jwt.JwtGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtGeneratorTest {
    private JwtGenerator jwtGenerator;
    private final String testEmail = "test@gmail.com";

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
        String token = jwtGenerator.generateToken(testEmail);

        String emailFromToken = jwtGenerator.extractUsername(token);

        Assertions.assertEquals(testEmail, emailFromToken);
        Assertions.assertTrue(jwtGenerator.isTokenValid(token, testEmail));
    }

    @Test
    public void testMalformedToken() {
        String token = "gdfgsfgshdgfjshgdfhjsgdjfhgsdjfgs";
        Exception e = Assertions.assertThrows(
                MalformedJwtException.class, () -> jwtGenerator.extractUsername(token)
        );
        System.out.println(e.getMessage());
    }

    @Test
    public void testExpiredToken() {
        ReflectionTestUtils.setField(jwtGenerator, "jwtExpirationTime", -1000);
        String token = jwtGenerator.generateToken(testEmail);

        Exception e = Assertions.assertThrows(
                ExpiredJwtException.class, () -> jwtGenerator.extractUsername(token)
        );
        System.out.println(e.getMessage());
    }
}
