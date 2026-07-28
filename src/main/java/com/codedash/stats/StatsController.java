package com.codedash.stats;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.handle.Platform;
import com.codedash.security.dto.UserPrincipal;
import com.codedash.stats.dto.HandleStatsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(
    name = "Statistics",
    description = "Retrieve coding platform statistics"
)
@SecurityRequirement(name = "Bearer Auth")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/{platform}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Get platform statistics",
        description = "Returns statistics for the authenticated user on the selected coding platform."
    )
    public HandleStatsResponse getStats(@PathVariable Platform platform, Authentication authentication) {
        UUID userId = getUserId(authentication);
        return statsService.getStats(userId, platform);
    }

    private UUID getUserId(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUserId();
    }
}