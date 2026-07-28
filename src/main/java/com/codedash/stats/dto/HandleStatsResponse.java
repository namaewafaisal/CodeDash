package com.codedash.stats.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Statistics for a student's coding platform handle")
public class HandleStatsResponse {

    @Schema(description = "Total problems solved", example = "542")
    private Integer problemsSolved;

    @Schema(description = "Easy problems solved", example = "215")
    private Integer easySolved;

    @Schema(description = "Medium problems solved", example = "281")
    private Integer mediumSolved;

    @Schema(description = "Hard problems solved", example = "46")
    private Integer hardSolved;

    @Schema(description = "Platform rating", example = "1785")
    private Integer rating;

    @Schema(description = "Global rank on the platform", example = "12543")
    private Integer globalRank;

    @Schema(description = "Time of the most recent accepted submission")
    private LocalDateTime lastSubmissionAt;

    @Schema(description = "Raw platform response")
    private Object rawData;

    @Schema(description = "Time when the statistics were last synchronized")
    private LocalDateTime lastSyncedAt;
}