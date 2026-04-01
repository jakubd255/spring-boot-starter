package com.example.springbootstarter.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class TokenCodeGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateTokenCode() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generateTokenCodeUUID() {
        return UUID.randomUUID().toString();
    }
}
