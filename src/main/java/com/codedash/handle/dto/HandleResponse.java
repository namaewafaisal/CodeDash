package com.codedash.handle.dto;

import java.time.LocalDateTime;

import com.codedash.handle.Platform;
import com.codedash.stats.dto.HandleStatsResponse;
import lombok.Data;

@Data
public class HandleResponse {

    private Long id;

    private Platform platform;

    private String username;

    private boolean verified;

    private LocalDateTime usernameUpdatedAt;

    private HandleStatsResponse stats;
}