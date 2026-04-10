package com.codedash.institution;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "institutions")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String domain;                    // e.g. trp.srmtrichy.edu.in

    @Enumerated(EnumType.STRING)
    private InstitutionStatus status;         // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt;

    // Runs before object creation
    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }
    
}
