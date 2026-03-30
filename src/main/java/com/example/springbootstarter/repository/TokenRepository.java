package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.Token;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Token> findByCode(String code);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.id = ?1")
    void deleteAllByUserId(Integer id);
}
