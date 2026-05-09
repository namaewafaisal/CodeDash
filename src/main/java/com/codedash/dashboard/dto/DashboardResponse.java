package com.codedash.dashboard.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DashboardResponse {

    private String fullName;
    private String registerNumber;

    private String username;
    private String platform;

    private Integer problemsSolved;
    private Integer easySolved;
    private Integer mediumSolved;
    private Integer hardSolved;

    private Integer globalRank;
    private Integer rating;

    private LocalDateTime lastSubmissionAt;  // for activity indicator
}