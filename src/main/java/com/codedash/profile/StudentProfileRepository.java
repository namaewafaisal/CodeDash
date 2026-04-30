package com.codedash.profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedash.user.User;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long>{

    Optional<StudentProfile> findByUser(User user);

	boolean existsByRegisterNumber(String registerNumber);
}
