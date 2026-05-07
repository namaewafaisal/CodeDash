package com.codedash.stats.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class HandleStatsResponse {

    private Integer problemsSolved;
    private Integer easySolved;
    private Integer mediumSolved;
    private Integer hardSolved;
    private Integer rating;
    private Integer globalRank;
    private LocalDateTime lastSubmissionAt;  // for activity indicator

    // Flexible — full API response
    private Object rawData;

    private LocalDateTime lastSyncedAt;

}