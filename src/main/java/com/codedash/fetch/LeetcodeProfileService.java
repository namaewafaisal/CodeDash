package com.codedash.fetch;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.handle.HandleRepository;
import com.codedash.handle.Platform;
import com.codedash.handle.StudentHandle;
import com.codedash.stats.HandleStats;
import com.codedash.stats.HandleStatsRepository;
import com.codedash.stats.dto.HandleStatsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeetcodeProfileService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final HandleRepository handleRepository;
    private final HandleStatsRepository handleStatsRepository;

    private final String BASE_URL = "http://localhost:3000";

    public HandleStatsResponse fetchAndStore(UUID userId) {

        // find handle by userId and platform
        StudentHandle handle = handleRepository
            .findByProfileUserIdAndPlatform(userId, Platform.LEETCODE)
            .orElseThrow(() -> new ResourceNotFoundException("LeetCode handle not found"));

        // fetch from API
        String url = BASE_URL + "/" + handle.getUsername() + "/profile";
        Map<String, Object> leetcodeResponse = restTemplate.getForObject(url, Map.class);

        // map to entity
        HandleStats stats = mapToEntity(leetcodeResponse);
        stats.setHandle(handle);

        // upsert — if stats row exists update it, else insert new
        handleStatsRepository
            .findByHandle(handle)
            .ifPresent(existing -> stats.setId(existing.getId()));

        HandleStats saved = handleStatsRepository.save(stats);
    
        // map to DTO — don't return entity
        HandleStatsResponse response = new HandleStatsResponse();
        response.setProblemsSolved(saved.getProblemsSolved());
        response.setEasySolved(saved.getEasySolved());
        response.setMediumSolved(saved.getMediumSolved());
        response.setHardSolved(saved.getHardSolved());
        response.setGlobalRank(saved.getGlobalRank());
        response.setLastSubmissionAt(saved.getLastSubmissionAt());
        response.setLastSyncedAt(saved.getLastSyncedAt());
        response.setRawData(saved.getRawData());
    
        return response;
    }

    private HandleStats mapToEntity(Map<String, Object> response) {

        if (response == null)
            throw new RuntimeException("LeetCode API returned null");

        HandleStats stats = new HandleStats();

        stats.setProblemsSolved((Integer) response.get("totalSolved"));
        stats.setEasySolved((Integer) response.get("easySolved"));
        stats.setMediumSolved((Integer) response.get("mediumSolved"));
        stats.setHardSolved((Integer) response.get("hardSolved"));
        stats.setGlobalRank((Integer) response.get("ranking"));

        // last submission timestamp
        List<Map<String, Object>> submissions =
            (List<Map<String, Object>>) response.get("recentSubmissions");

        if (submissions != null && !submissions.isEmpty()) {
            String ts = (String) submissions.get(0).get("timestamp");
            stats.setLastSubmissionAt(
                Instant.ofEpochSecond(Long.parseLong(ts))
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }

        // ---- raw data — only recent submissions ----
        try {
            Map<String, Object> rawDataMap = new LinkedHashMap<>();
            rawDataMap.put("recentSubmissions", response.get("recentSubmissions"));
        
            stats.setRawData(objectMapper.writeValueAsString(rawDataMap));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize raw data", e);
        }
        

        stats.setLastSyncedAt(LocalDateTime.now());

        return stats;
    }
}