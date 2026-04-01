package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.type.Role;
import com.example.springbootstarter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    long countByRole(Role role);
    boolean existsByEmail(String email);
}
