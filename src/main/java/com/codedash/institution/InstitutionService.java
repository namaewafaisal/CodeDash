package com.codedash.institution;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedash.exceptionhandlers.AlreadyExistsException;
import com.codedash.exceptionhandlers.BadRequestException;
import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.institution.dto.InstitutionRegisterRequest;
import com.codedash.user.Role;
import com.codedash.user.User;
import com.codedash.user.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Institution> all(){
        return institutionRepository.findAll();
    }
    
    public void registerInstitution(InstitutionRegisterRequest request) {

        // 1. Block generic domains
        List<String> blockedDomains = List.of("gmail.com","yahoo.com","hotmail.com","outlook.com");
        if (blockedDomains.contains(request.getDomain().toLowerCase())) {
            throw new BadRequestException("Generic email domains are not allowed");
        }

        // 2. Check domain not already registered
        if (institutionRepository.existsByDomain(request.getDomain())) {
            throw new AlreadyExistsException("Domain already registered");
        }

        if (!request.getAdminEmail().endsWith(request.getDomain())) {
            throw new BadRequestException("Email is not of the given domain");
        }
        // 3. Create institution with PENDING status
        Institution institution = new Institution();
        institution.setName(request.getInstitutionName());
        institution.setDomain(request.getDomain());
        institution.setStatus(InstitutionStatus.PENDING);
        institutionRepository.save(institution);

        // 4. Create admin user (not yet active — institution pending)
        User admin = new User();
        admin.setEmail(request.getAdminEmail());
        admin.setPassword(passwordEncoder.encode(request.getAdminPassword()));
        admin.setRole(Role.INSTITUTION_ADMIN);
        admin.setInstitution(institution);
        admin.setEmailVerified(false);
        userRepository.save(admin);

        // 5. Email Master for approval
        // emailService.sendInstitutionApprovalRequest(
        //     institution, admin, masterEmail
        // );
    }

    public void handleInstitution(Long id, InstitutionStatus status) {

        Institution institution = institutionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

        if (status == InstitutionStatus.APPROVED) {

            institution.setStatus(InstitutionStatus.APPROVED);

            User admin = userRepository.findByInstitution(institution)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

            admin.setEmailVerified(true);
            userRepository.save(admin);

        } else if (status == InstitutionStatus.REJECTED) {

            institution.setStatus(InstitutionStatus.REJECTED);

        } else {
            throw new BadRequestException("Invalid status");
        }

        institutionRepository.save(institution);
    }
}
