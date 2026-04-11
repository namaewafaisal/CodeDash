package com.codedash.registration;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pending_institutions")
public class PendingInstitution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String domain;

    // Admin credentials — stored until approved, then moved
    private String adminEmail;
    private String adminPassword;       // bcrypt hashed

    private LocalDateTime requestedAt;

    @PrePersist
    public void prePersist() { this.requestedAt = LocalDateTime.now(); }
}