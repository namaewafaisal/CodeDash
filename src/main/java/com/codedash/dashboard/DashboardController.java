package com.codedash.dashboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.dashboard.dto.DashboardResponse;
import com.codedash.security.dto.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('INSTITUTION_ADMIN', 'STAFF')")
@SecurityRequirement(name = "Bearer Auth")
@Tag(
    name = "Dashboard",
    description = "Leaderboard and coding performance dashboard endpoints"
)
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
        summary = "Get institution leaderboard",
        description = "Returns a paginated leaderboard of students for the authenticated user's institution."
    )
    @GetMapping("/leaderboard")
    public Page<DashboardResponse> getLeaderboard(
            Authentication authentication,
            @PageableDefault(
                sort = "problemsSolved",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {

        Long institutionId = getInstitutionId(authentication);
        return dashboardService.getLeaderboard(institutionId, pageable);
    }

    private Long getInstitutionId(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getInstitutionId();
    }
}