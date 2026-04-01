package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.entity.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @EntityGraph(attributePaths = {"user"})
    @NotNull
    Optional<Session> findById(@NotNull Integer id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Session> findByToken(@NotNull String token);

    @EntityGraph(attributePaths = {"user"})
    List<Session> findByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.user.id = ?1")
    void deleteByUserId(Integer userId);
}