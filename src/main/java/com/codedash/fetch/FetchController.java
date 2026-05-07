package com.codedash.fetch;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.UserPrincipal;
import com.codedash.handle.dto.HandleResponse;
import com.codedash.stats.HandleStats;
import com.codedash.stats.dto.HandleStatsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fetch")
@RequiredArgsConstructor
public class FetchController {

    private final FetchService fetchService;
    private final LeetcodeProfileService leetcodeProfileService;

    // ---------------- GITHUB ----------------
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/github")
    public HandleResponse fetchGithub(
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return fetchService.fetchGithub(userId);
    }

    // ---------------- LEETCODE ----------------
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/leetcode")
    public HandleStatsResponse fetchLeetcode(
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return leetcodeProfileService.fetchAndStore(userId);
    }

    // ---------------- helper ----------------
    private UUID getUserId(Authentication authentication) {

        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        return userPrincipal.getUserId();
    }
}