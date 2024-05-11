package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.Role;
import com.example.springbootstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    long countByRole(Role role);
}
