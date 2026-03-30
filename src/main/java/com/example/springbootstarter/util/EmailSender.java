package com.example.springbootstarter.util;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    public void sendVerificationEmail(String to, String code) {
        String link = "http://localhost:8080/api/auth/verify?token="+code;

        System.out.println(to+" --- "+link);
    }

    public void sendResetPassword(String to, String code) {
        String link = "http://localhost:8080/api/auth/reset-password?token="+code;

        System.out.println(to+" --- "+link);
    }
}
