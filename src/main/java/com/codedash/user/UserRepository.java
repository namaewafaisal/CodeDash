package com.codedash.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedash.institution.Institution;

public interface UserRepository extends JpaRepository<User, UUID>{

    Optional<User> findByInstitution(Institution institution);

    Optional<User> findByEmail(String email);


}
