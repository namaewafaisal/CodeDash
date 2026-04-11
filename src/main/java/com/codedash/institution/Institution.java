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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "institutions")
@Getter
@Setter
@NoArgsConstructor
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
