package com.codedash.fetch;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.handle.HandleRepository;
import com.codedash.handle.Platform;
import com.codedash.handle.StudentHandle;
import com.codedash.handle.dto.HandleResponse;
import com.codedash.stats.dto.HandleStatsResponse;
import com.codedash.stats.HandleStats;
import com.codedash.stats.HandleStatsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FetchService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final HandleRepository handleRepository;
    private final HandleStatsRepository handleStatsRepository;

    private final ObjectMapper objectMapper;

    @Value("${app.frontend-url}")
    private String LEETCODE_BASE_URL;

    // =========================================================
    // LEETCODE
    // =========================================================
    public HandleResponse fetchLeetcode(UUID userId) {

        // ---- find handle ----
        StudentHandle handle =
                handleRepository
                        .findByProfileUserIdAndPlatform(
                                userId,
                                Platform.LEETCODE
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "LeetCode handle not found"
                                ));

        // ---- fetch api ----
        String username = handle.getUsername();

        String url =
                LEETCODE_BASE_URL + "/" + username + "/profile";

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new RuntimeException(
                    "LeetCode API returned null"
            );
        }

        // ---- map stats ----
        HandleStats stats = mapLeetcodeStats(response);

        // ---- connect handle ----
        stats.setHandle(handle);

        // ---- save stats ----
        handleStatsRepository
                .findByHandle(handle)
                .ifPresent(existing -> stats.setId(existing.getId()));

        HandleStats savedStats =
                handleStatsRepository.save(stats);

        handle.setStats(savedStats);

        // ---- return dto ----
        return mapToResponse(handle);
    }

    // =========================================================
    // GITHUB
    // =========================================================
    public HandleResponse fetchGithub(UUID userId) {
    
        StudentHandle handle =
                handleRepository
                        .findByProfileUserIdAndPlatform(
                                userId,
                                Platform.GITHUB
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Github handle not found"
                                ));
    
        String username = handle.getUsername();
    
        String url =
                "https://api.github.com/users/" + username;
    
        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);
    
        if (response == null) {
            throw new RuntimeException(
                    "Github API returned null"
            );
        }
    
        // ---- map stats ----
        HandleStats stats = mapGithubStats(response);
    
        // ---- connect handle ----
        stats.setHandle(handle);
    
        // ---- update existing ----
        handleStatsRepository
                .findByHandle(handle)
                .ifPresent(existing -> stats.setId(existing.getId()));
    
        HandleStats savedStats =
                handleStatsRepository.save(stats);
    
        handle.setStats(savedStats);
    
        // ---- return dto ----
        return mapToResponse(handle);
    }

    // =========================================================
    // LEETCODE MAPPER
    // =========================================================
    private HandleStats mapLeetcodeStats(
            Map<String, Object> response) {

        HandleStats stats = new HandleStats();

        // ---- basic stats ----
        stats.setProblemsSolved(
                (Integer) response.get("totalSolved"));

        stats.setEasySolved(
                (Integer) response.get("easySolved"));

        stats.setMediumSolved(
                (Integer) response.get("mediumSolved"));

        stats.setHardSolved(
                (Integer) response.get("hardSolved"));

        stats.setGlobalRank(
                (Integer) response.get("ranking"));

        // ---- recent submissions ----
        List<Map<String, Object>> submissions =
                (List<Map<String, Object>>)
                        response.get("recentSubmissions");

        if (submissions != null && !submissions.isEmpty()) {

            String ts =
                    (String) submissions
                            .get(0)
                            .get("timestamp");

            LocalDateTime lastSubmission =
                    Instant
                            .ofEpochSecond(
                                    Long.parseLong(ts)
                            )
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDateTime();

            stats.setLastSubmissionAt(lastSubmission);
        }

        // ---- raw json ----
        try {
            stats.setRawData(
                    objectMapper.writeValueAsString(response)
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to serialize raw data",
                    e
            );
        }

        // ---- sync time ----
        stats.setLastSyncedAt(LocalDateTime.now());

        return stats;
    }

    // =========================================================
    // DTO MAPPER
    // =========================================================
    private HandleResponse mapToResponse(
            StudentHandle handle) {

        HandleResponse response =
                new HandleResponse();

        response.setId(handle.getId());
        response.setPlatform(handle.getPlatform());
        response.setUsername(handle.getUsername());
        response.setVerified(handle.isVerified());
        response.setUsernameUpdatedAt(
                handle.getUsernameUpdatedAt()
        );

        if (handle.getStats() != null) {

            HandleStats stats = handle.getStats();

            HandleStatsResponse statsResponse =
                    new HandleStatsResponse();

            statsResponse.setProblemsSolved(
                    stats.getProblemsSolved());

            statsResponse.setEasySolved(
                    stats.getEasySolved());

            statsResponse.setMediumSolved(
                    stats.getMediumSolved());

            statsResponse.setHardSolved(
                    stats.getHardSolved());

            statsResponse.setRating(
                    stats.getRating());

            statsResponse.setGlobalRank(
                    stats.getGlobalRank());

            statsResponse.setLastSubmissionAt(
                    stats.getLastSubmissionAt());

            statsResponse.setLastSyncedAt(
                    stats.getLastSyncedAt());

            response.setStats(statsResponse);
        }

        return response;
    }

    private HandleStats mapGithubStats(
            Map<String, Object> response) {
    
        HandleStats stats = new HandleStats();
    
        // github does not have these
        stats.setProblemsSolved(null);
        stats.setEasySolved(null);
        stats.setMediumSolved(null);
        stats.setHardSolved(null);
        stats.setRating(null);
        stats.setGlobalRank(null);
    
        // ---- sync time ----
        stats.setLastSyncedAt(LocalDateTime.now());
    
        // ---- raw json ----
        try {
    
            stats.setRawData(
                    objectMapper.writeValueAsString(response)
            );
    
        } catch (Exception e) {
    
            throw new RuntimeException(
                    "Failed to serialize github raw data",
                    e
            );
        }
    
        return stats;
    }
}