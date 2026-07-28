package com.codedash.dashboard.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Student leaderboard entry")
public class DashboardResponse {

    @Schema(
        description = "Student's full name",
        example = "Mohamed Faizal"
    )
    private String fullName;

    @Schema(
        description = "Student register number",
        example = "814723104089"
    )
    private String registerNumber;

    @Schema(
        description = "Coding platform username",
        example = "faizal123"
    )
    private String username;

    @Schema(
        description = "Coding platform",
        example = "LEETCODE"
    )
    private String platform;

    @Schema(
        description = "Total problems solved",
        example = "542"
    )
    private Integer problemsSolved;

    @Schema(
        description = "Easy problems solved",
        example = "215"
    )
    private Integer easySolved;

    @Schema(
        description = "Medium problems solved",
        example = "281"
    )
    private Integer mediumSolved;

    @Schema(
        description = "Hard problems solved",
        example = "46"
    )
    private Integer hardSolved;

    @Schema(
        description = "Global ranking on the selected platform",
        example = "12543"
    )
    private Integer globalRank;

    @Schema(
        description = "Platform rating",
        example = "1785"
    )
    private Integer rating;

    @Schema(
        description = "Timestamp of the most recent accepted submission"
    )
    private LocalDateTime lastSubmissionAt;
}