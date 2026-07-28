package com.codedash.handle.dto;

import java.time.LocalDateTime;

import com.codedash.handle.Platform;
import com.codedash.stats.dto.HandleStatsResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Coding platform handle")
public class HandleResponse {

    @Schema(description = "Handle ID", example = "1")
    private Long id;

    @Schema(description = "Coding platform", example = "LEETCODE")
    private Platform platform;

    @Schema(description = "Username", example = "faizal123")
    private String username;

    @Schema(description = "Whether the handle has been verified")
    private boolean verified;

    @Schema(description = "Last time the username was updated")
    private LocalDateTime usernameUpdatedAt;

    @Schema(description = "Statistics for this handle")
    private HandleStatsResponse stats;
}