package com.example.springbootstarter.controller;

import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.query.request.UserQuery;
import com.example.springbootstarter.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@ModelAttribute UserQuery query) {
        List<UserDto> users = userService.findAll(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        userService.exportToCsv(response);
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        UserDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
