package com.codedash.handle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.handle.dto.HandleRequest;
import com.codedash.handle.dto.HandleResponse;
import com.codedash.stats.dto.HandleStatsResponse;
import com.codedash.handle.dto.UpdateHandleRequest;
import com.codedash.profile.StudentProfile;
import com.codedash.profile.StudentProfileRepository;
import com.codedash.stats.HandleStats;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandleService {

    private final HandleRepository handleRepository;
    private final StudentProfileRepository studentProfileRepository;

    // ---------------- GET ALL ----------------
    public List<HandleResponse> getHandles(UUID userId) {

        return handleRepository.findByProfileUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ---------------- GET ONE ----------------
    public HandleResponse getHandle(
            UUID userId,
            Platform platform) {
                
        StudentHandle handle =
                handleRepository
                        .findByProfileUserIdAndPlatform(userId, platform)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Handle not found"));

        return mapToResponse(handle);
    }

    // ---------------- CREATE ----------------
    public void createHandle(
            UUID userId,
            HandleRequest request) {

        boolean exists =
                handleRepository.existsByProfileUserIdAndPlatform(
                        userId,
                        request.getPlatform()
                );

        if (exists) {
            throw new RuntimeException("Handle already exists");
        }

        StudentProfile profile =
                studentProfileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Profile not found"));

        StudentHandle handle = new StudentHandle();

        handle.setProfile(profile);
        handle.setPlatform(request.getPlatform());
        handle.setUsername(request.getUsername());
        handle.setVerified(false);
        handle.setUsernameUpdatedAt(LocalDateTime.now());

        handleRepository.save(handle);
    }

    // ---------------- UPDATE ----------------
    public void updateHandle(
            UUID userId,
            Platform platform,
            UpdateHandleRequest request) {

        StudentHandle handle =
                handleRepository
                        .findByProfileUserIdAndPlatform(userId, platform)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Handle not found"));

        handle.setUsername(request.getUsername());
        handle.setUsernameUpdatedAt(LocalDateTime.now());

        handleRepository.save(handle);
    }

    // ---------------- DELETE ----------------
    public void deleteHandle(
            UUID userId,
            Platform platform) {

        StudentHandle handle =
                handleRepository
                        .findByProfileUserIdAndPlatform(userId, platform)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Handle not found"));

        handleRepository.delete(handle);
    }

    // ---------------- MAPPER ----------------
    private HandleResponse mapToResponse(StudentHandle handle) {

        HandleResponse response = new HandleResponse();

        response.setId(handle.getId());
        response.setPlatform(handle.getPlatform());
        response.setUsername(handle.getUsername());
        response.setVerified(handle.isVerified());
        response.setUsernameUpdatedAt(handle.getUsernameUpdatedAt());

        if (handle.getStats() != null) {

            HandleStats stats = handle.getStats();

            HandleStatsResponse statsResponse =
                    new HandleStatsResponse();

            statsResponse.setProblemsSolved(stats.getProblemsSolved());
            statsResponse.setEasySolved(stats.getEasySolved());
            statsResponse.setMediumSolved(stats.getMediumSolved());
            statsResponse.setHardSolved(stats.getHardSolved());
            statsResponse.setRating(stats.getRating());
            statsResponse.setGlobalRank(stats.getGlobalRank());
            statsResponse.setLastSubmissionAt(stats.getLastSubmissionAt());
            statsResponse.setLastSyncedAt(stats.getLastSyncedAt());

            response.setStats(statsResponse);
        }

        return response;
    }
}