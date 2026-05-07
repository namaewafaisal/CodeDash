package com.codedash.handle.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HandleStatsResponse {

    private Integer problemsSolved;

    private Integer easySolved;

    private Integer mediumSolved;

    private Integer hardSolved;

    private Integer rating;

    private Integer globalRank;

    private LocalDateTime lastSubmissionAt;

    private LocalDateTime lastSyncedAt;
}