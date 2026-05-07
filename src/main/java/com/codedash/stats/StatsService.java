package com.codedash.stats;

import org.springframework.stereotype.Service;

import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.stats.dto.HandleStatsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.codedash.handle.Platform;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final HandleStatsRepository handleStatsRepository;
    private final ObjectMapper objectMapper;

    public HandleStatsResponse getStats(UUID userId, Platform platform) {

        HandleStats stats = handleStatsRepository
            .findByHandleProfileUserIdAndHandlePlatform(userId, platform)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Stats not found for platform: " + platform
            ));

        HandleStatsResponse response = new HandleStatsResponse();
        response.setProblemsSolved(stats.getProblemsSolved());
        response.setEasySolved(stats.getEasySolved());
        response.setMediumSolved(stats.getMediumSolved());
        response.setHardSolved(stats.getHardSolved());
        response.setRating(stats.getRating());
        response.setGlobalRank(stats.getGlobalRank());
        response.setLastSubmissionAt(stats.getLastSubmissionAt());
        response.setLastSyncedAt(stats.getLastSyncedAt());

        try {
            response.setRawData(objectMapper.readValue(stats.getRawData(), Object.class));
        } catch (Exception e) {
            response.setRawData(stats.getRawData()); // fallback
        }

        return response;
    }
}