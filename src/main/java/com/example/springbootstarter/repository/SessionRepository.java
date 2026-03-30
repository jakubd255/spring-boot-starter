package com.example.springbootstarter.repository;

import com.example.springbootstarter.model.Session;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Session> findSessionById(Integer id);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.user.id = ?1")
    void deleteSessionsByUserId(Integer userId);
}