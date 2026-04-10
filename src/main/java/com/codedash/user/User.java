package com.codedash.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.codedash.institution.Institution;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;                  // bcrypt

    @Enumerated(EnumType.STRING)
    private Role role;                        // MASTER, INSTITUTION_ADMIN, STAFF, STUDENT

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;          // null for MASTER

    private boolean emailVerified;

    private String verificationToken;        // UUID token sent in email

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }
}