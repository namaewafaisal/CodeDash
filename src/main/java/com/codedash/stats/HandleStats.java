package com.codedash.stats;

import java.time.LocalDateTime;

import com.codedash.handle.StudentHandle;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "handle_stats")
@Data
public class HandleStats {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "handle_id", unique = true, nullable = false)
    private StudentHandle handle;

    // Structured — for sorting and filtering
    private Integer problemsSolved;
    private Integer easySolved;
    private Integer mediumSolved;
    private Integer hardSolved;
    private Integer rating;
    private Integer globalRank;
    private LocalDateTime lastSubmissionAt;  // for activity indicator

    // Flexible — full API response
    @Column(columnDefinition = "jsonb")
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    private String rawData;

    private LocalDateTime lastSyncedAt;
}