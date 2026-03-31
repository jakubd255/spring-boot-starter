package com.example.springbootstarter.controller;

import com.example.springbootstarter.dto.converter.UserDtoMapper;
import com.example.springbootstarter.dto.request.UpdateRoleRequest;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.query.request.UserQuery;
import com.example.springbootstarter.service.UserService;
import com.example.springbootstarter.util.csv.UserCsvExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<List<UserDto>> getAll(@ModelAttribute UserQuery query) {
        Specification<User> spec = query.toSpecification();
        Pageable pageable = query.toPageable();

        List<User> users = userService.getAll(spec, pageable);
        return ResponseEntity.ok(UserDtoMapper.mapUsersToDto(users));
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/csv")
    public void exportToCsv(@ModelAttribute UserQuery query, HttpServletResponse response) throws IOException {
        List<User> users = userService.getAll();

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

        UserCsvExporter exporter = new UserCsvExporter(response.getWriter());
        exporter.generateCsv(users);
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(user));
    }

    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/{id}/active")
    public ResponseEntity<UserDto> toggleActiveById(@PathVariable Integer id) {
        User user = userService.toggleActiveById(id);
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(user));
    }

    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserDto> updateRoleById(@PathVariable Integer id, @RequestBody UpdateRoleRequest request) {
        User user = userService.updateRoleById(id, request);
        return ResponseEntity.ok(UserDtoMapper.mapUserToDto(user));
    }

    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
