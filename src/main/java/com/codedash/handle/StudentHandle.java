package com.codedash.handle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.codedash.profile.StudentProfile;
import com.codedash.stats.HandleStats;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "student_handles",
    uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "platform"})
)
@Data
public class StudentHandle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private StudentProfile profile;

    @Enumerated(EnumType.STRING)
    private Platform platform;               // LEETCODE, CODEFORCES, GITHUB, etc.

    private String username;

    private boolean verified;

    private LocalDateTime usernameUpdatedAt;

    private LocalDate lastFetchedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FetchFrequency fetchFrequency
            = FetchFrequency.NEVER;

    private LocalDate nextFetchDate;


    @OneToOne(mappedBy = "handle", cascade = CascadeType.ALL)
    private HandleStats stats;
}