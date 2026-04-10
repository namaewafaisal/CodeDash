package com.codedash.profile;

import java.time.LocalDateTime;
import java.util.List;

import com.codedash.handle.StudentHandle;
import com.codedash.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private String fullName;

    @Column(unique = true)
    private String registerNumber;

    private String department;
    private Integer year;
    private String section;
    private String trainingBatch;
    private String phone;
    private String personalEmail;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<StudentHandle> handles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist public void prePersist() { this.createdAt = LocalDateTime.now(); }
    @PreUpdate  public void preUpdate()  { this.updatedAt = LocalDateTime.now(); }
}