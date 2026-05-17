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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/{platform}")
    @PreAuthorize("isAuthenticated()")
    public HandleStatsResponse getStats(@PathVariable Platform platform, Authentication authentication) {
        UUID userId = getUserId(authentication);
        return statsService.getStats(userId,platform);
    }

    private UUID getUserId(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUserId();
    }
	
}