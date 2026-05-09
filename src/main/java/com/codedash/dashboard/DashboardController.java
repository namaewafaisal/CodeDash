package com.codedash.dashboard;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.UserPrincipal;
import com.codedash.dashboard.dto.DashboardResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/leaderboard")
  @PreAuthorize("hasAnyRole('INSTITUTION_ADMIN', 'STAFF')")
  public Page<DashboardResponse> getLeaderboard(
      Authentication authentication, 
      @PageableDefault(sort = "problemsSolved", direction = Sort.Direction.DESC) Pageable pageable) {
      Long institutionId = getInstitutionId(authentication);
      return dashboardService.getLeaderboard(institutionId, pageable);
  } 

  private Long getInstitutionId(Authentication authentication) {
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      return userPrincipal.getInstitutionId();
  }

}
