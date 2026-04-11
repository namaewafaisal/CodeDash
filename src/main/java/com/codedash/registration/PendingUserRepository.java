package com.codedash.registration;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedash.institution.Institution;

public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {

    Optional<PendingUser> findByEmail(String email);

    Optional<PendingUser> findByVerificationToken(String token);

}
