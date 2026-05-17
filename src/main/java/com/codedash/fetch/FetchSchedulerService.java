package com.codedash.fetch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.codedash.handle.HandleRepository;
import com.codedash.handle.Platform;
import com.codedash.handle.StudentHandle;
import com.codedash.stats.HandleStats;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FetchSchedulerService {

    private final HandleRepository handleRepository;
    private final LeetcodeFetchService leetcodeFetchService;

    @Scheduled(cron = "0 0 2 * * *")
    public void syncLeetcodeHandles() {
        System.out.println("Scheduler running");
    
        LocalDate today = LocalDate.now();
    
        List<StudentHandle> handles =
                handleRepository
                        .findByPlatformAndNextFetchDateLessThanEqual(
                                Platform.LEETCODE,
                                today
                        );
    
        for (StudentHandle handle : handles) {
    
            try {
    
                HandleStats stats =
                        leetcodeFetchService
                                .fetchStats(handle);
    
                HandleStats existingStats =
                        handle.getStats();
    
                if (existingStats == null) {
    
                    stats.setHandle(handle);
    
                    handle.setStats(stats);
    
                } else {
    
                    existingStats.setProblemsSolved(
                            stats.getProblemsSolved());
    
                    existingStats.setEasySolved(
                            stats.getEasySolved());
    
                    existingStats.setMediumSolved(
                            stats.getMediumSolved());
    
                    existingStats.setHardSolved(
                            stats.getHardSolved());
    
                    existingStats.setGlobalRank(
                            stats.getGlobalRank());
    
                    existingStats.setRating(
                            stats.getRating());
    
                    existingStats.setLastSubmissionAt(
                            stats.getLastSubmissionAt());
    
                    existingStats.setLastSyncedAt(
                            stats.getLastSyncedAt());
    
                    existingStats.setRawData(
                            stats.getRawData());
                }

                handle.setLastFetchedDate(LocalDate.now());
                updateNextFetchDate(handle, today);
    
                handleRepository.save(handle);
    
            } catch (Exception e) {
    
                System.out.println(
                        "Failed to sync handle: "
                        + handle.getUsername()
                );
            }
        }
    }
    private void updateNextFetchDate(
            StudentHandle handle,
            LocalDate today
    ) {
    
        switch (handle.getFetchFrequency()) {
    
            case DAILY ->
                    handle.setNextFetchDate(
                            today.plusDays(1));
    
            case WEEKLY ->
                    handle.setNextFetchDate(
                            today.plusWeeks(1));

            case MONTHLY -> 
            handle.setNextFetchDate(
                            today.plusMonths(1));
    
            case NEVER ->
                    handle.setNextFetchDate(null);
        }
    }

}