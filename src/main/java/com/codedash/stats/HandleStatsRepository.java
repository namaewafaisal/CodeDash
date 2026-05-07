package com.codedash.stats;

import java.util.Optional;

import com.codedash.handle.StudentHandle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HandleStatsRepository extends JpaRepository<HandleStats, Long>{
    Optional<HandleStats> findByHandle(StudentHandle handle);
}
