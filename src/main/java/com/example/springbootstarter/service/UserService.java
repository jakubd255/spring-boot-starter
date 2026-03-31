package com.example.springbootstarter.service;

import com.example.springbootstarter.dto.request.UpdateRoleRequest;
import com.example.springbootstarter.dto.request.UpdateUserRequest;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).getContent();
    }

    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Invalid email"));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User update(User user, UpdateUserRequest request) {
        Optional.ofNullable(request.getFullName())
                .ifPresent(user::setFullName);
        return userRepository.save(user);
    }

    public User updateRole(User user, UpdateRoleRequest request) {
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    public User toggleActive(User user) {
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
