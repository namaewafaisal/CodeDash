package com.codedash.fetch;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedash.handle.StudentHandle;
import com.codedash.stats.HandleStats;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeetcodeProfileService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    private final String BASE_URL = "http://localhost:3000";

    public HandleStats fetchProfile(String username) {

        // String username = handle.getUsername();
        String url = BASE_URL + "/" + username + "/profile";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return mapToEntity(response);
    }

    // -------------------------
    // Mapping logic (important)
    // -------------------------
    private HandleStats mapToEntity(Map<String, Object> response) {

        if (response == null) {
            throw new RuntimeException("LeetCode API returned null");
        }

        HandleStats stats = new HandleStats();

        // ---- basic stats ----
        stats.setProblemsSolved((Integer) response.get("totalSolved"));
        stats.setEasySolved((Integer) response.get("easySolved"));
        stats.setMediumSolved((Integer) response.get("mediumSolved"));
        stats.setHardSolved((Integer) response.get("hardSolved"));
        stats.setGlobalRank((Integer) response.get("ranking"));


        List<Map<String, Object>> submissions =
                (List<Map<String, Object>>) response.get("recentSubmissions");

        if (submissions != null && !submissions.isEmpty()) {
            String ts = (String) submissions.get(0).get("timestamp");

            LocalDateTime lastSubmission = Instant
                    .ofEpochSecond(Long.parseLong(ts))
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            stats.setLastSubmissionAt(lastSubmission);
        }
        
        try {
            stats.setRawData(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize raw data", e);
        }

        stats.setLastSyncedAt(LocalDateTime.now());
        
        return stats;
    }
}