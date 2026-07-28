package com.codedash.fetch;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.handle.dto.HandleResponse;
import com.codedash.security.dto.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fetch")
@RequiredArgsConstructor
@Tag(
    name = "Fetch",
    description = "Fetch coding platform data for the authenticated student"
)
@SecurityRequirement(name = "Bearer Auth")
public class FetchController {

    private final FetchService fetchService;
    private final LeetcodeFetchService leetcodeProfileService;

    // ---------------- GITHUB ----------------
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/github")
    @Operation(
        summary = "Fetch GitHub profile",
        description = "Fetches and updates the authenticated student's GitHub profile information."
    )
    public HandleResponse fetchGithub(
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return fetchService.fetchGithub(userId);
    }

    // ---------------- LEETCODE ----------------
    // @PreAuthorize("hasRole('STUDENT')")
    // @GetMapping("/leetcode")
    // @Operation(
    //     summary = "Fetch LeetCode statistics",
    //     description = "Fetches and updates the authenticated student's LeetCode statistics."
    // )
    // public HandleStatsResponse fetchLeetcode(
    //         Authentication authentication) {
    //
    //     UUID userId = getUserId(authentication);
    //
    //     return leetcodeProfileService.fetchStats(userId);
    // }

    // ---------------- helper ----------------
    private UUID getUserId(Authentication authentication) {

        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        return userPrincipal.getUserId();
    }
}