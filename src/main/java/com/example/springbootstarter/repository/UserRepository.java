package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.Role;
import com.example.springbootstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    long countByRole(Role role);
}
