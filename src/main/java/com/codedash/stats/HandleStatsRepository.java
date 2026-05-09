package com.codedash.stats;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codedash.handle.Platform;
import com.codedash.handle.StudentHandle;

public interface HandleStatsRepository extends JpaRepository<HandleStats, Long>{
    Optional<HandleStats> findByHandle(StudentHandle handle);

    
    Optional<HandleStats> findByHandleProfileUserIdAndHandlePlatform(
        UUID userId, Platform platform
    );

    Page<HandleStats> findByHandleProfileUserInstitutionId(Long institutionId, Pageable pageable);
}
