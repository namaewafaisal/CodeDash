package com.codedash.registration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingInstitutionRepository extends JpaRepository<PendingInstitution, Long>{

    boolean existsByDomain(String domain);

}
