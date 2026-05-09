package com.codedash.dashboard;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codedash.dashboard.dto.DashboardResponse;
import com.codedash.handle.StudentHandle;
import com.codedash.mapper.AppMapper;
import com.codedash.profile.StudentProfile;
import com.codedash.stats.HandleStats;
import com.codedash.stats.HandleStatsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final HandleStatsRepository handleStatsRepository;
    
    public Page<DashboardResponse> getLeaderboard(Long institutionId, Pageable pageable) {
        Page<HandleStats> stats =  handleStatsRepository.findByHandleProfileUserInstitutionId(institutionId,pageable);

        return stats.map(this::toDashboardResponse);
    }
    public DashboardResponse toDashboardResponse(
            HandleStats stats) {
    
        DashboardResponse response =
                new DashboardResponse();
    
        StudentHandle handle =
                stats.getHandle();
    
        StudentProfile profile =
                handle.getProfile();
    
        // profile fields
        response.setFullName(
                profile.getFullName());
    
        response.setRegisterNumber(
                profile.getRegisterNumber());
    
        // handle fields
        response.setUsername(
                handle.getUsername());
    
        response.setPlatform(
                handle.getPlatform().name());
    
        // stats fields
        response.setProblemsSolved(
                stats.getProblemsSolved());
    
        response.setEasySolved(
                stats.getEasySolved());
    
        response.setMediumSolved(
                stats.getMediumSolved());
    
        response.setHardSolved(
                stats.getHardSolved());
    
        response.setGlobalRank(
                stats.getGlobalRank());
    
        return response;
    }
}
