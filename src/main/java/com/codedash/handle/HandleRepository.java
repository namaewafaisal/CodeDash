package com.codedash.handle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface HandleRepository
        extends JpaRepository<StudentHandle, Long> {

    Optional<StudentHandle>
    findByProfileUserIdAndPlatform(UUID userId, Platform platform);

    List<StudentHandle>
    findByProfileUserId(UUID userId);

    boolean existsByProfileUserIdAndPlatform(
            UUID userId,
            Platform platform
    );
    List<StudentHandle> findByProfileUserIdInAndPlatform(
            List<UUID> userIds,
            Platform platform
    );

    List<StudentHandle> findByPlatformAndNextFetchDateLessThanEqual(
        Platform platform,
        LocalDate today
    );
    
}