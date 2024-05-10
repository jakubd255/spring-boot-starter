package com.example.springbootstarter.controller;

import com.example.springbootstarter.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        userService.exportToCsv(response);
    }
}
