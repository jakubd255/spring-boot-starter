package com.example.springbootstarter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "sessions")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String token;

    private String os;

    private String browser;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private Date expiresAt = new Date(System.currentTimeMillis()+1000L*60*60*24*30);

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}