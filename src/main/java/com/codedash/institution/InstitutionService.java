package com.codedash.institution;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedash.exceptionhandlers.AlreadyExistsException;
import com.codedash.exceptionhandlers.BadRequestException;
import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.institution.dto.InstitutionRegisterRequest;
import com.codedash.registration.PendingInstitution;
import com.codedash.registration.PendingInstitutionRepository;
import com.codedash.registration.PendingUserRepository;
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
    private final PendingInstitutionRepository pendingInstitutionRepository;
    private final PendingUserRepository pendingUserRepository;

    public List<Institution> getAll(){
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

        // Domain must not already be pending
        if (pendingInstitutionRepository.existsByDomain(request.getDomain())){
            throw new BadRequestException("Registration request already submitted for this domain");
        }

        if (!request.getAdminEmail().endsWith(request.getDomain())) {
            throw new BadRequestException("Email is not of the given domain");
        }

        // 3. Create institution with PENDING status
        PendingInstitution pending = new PendingInstitution();
        pending.setName(request.getInstitutionName());
        pending.setDomain(request.getDomain());
        pending.setAdminEmail(request.getAdminEmail());
        pending.setAdminPassword(passwordEncoder.encode(request.getAdminPassword()));
        pendingInstitutionRepository.save(pending);


        // 4. Email Master for approval
        // emailService.sendInstitutionApprovalRequest(
        //     institution, admin, masterEmail
        // );
    }

    public void approve(Long pendingId) {

        PendingInstitution pending = pendingInstitutionRepository.findById(pendingId)
            .orElseThrow(() -> new ResourceNotFoundException("Pending institution not found"));

        // Move to institutions table
        Institution institution = new Institution();
        institution.setName(pending.getName());
        institution.setDomain(pending.getDomain());
        institution.setCreatedAt(LocalDateTime.now());
        institutionRepository.save(institution);

        // Create admin user in users table
        User admin = new User();
        admin.setEmail(pending.getAdminEmail());
        admin.setPassword(pending.getAdminPassword());  // already hashed
        admin.setRole(Role.INSTITUTION_ADMIN);
        admin.setInstitution(institution);
        userRepository.save(admin);

        // Delete from pending
        pendingInstitutionRepository.delete(pending);

        // Email admin — you're approved
        // emailService.sendInstitutionApproved(admin.getEmail(), institution.getName());
    }

    public void reject(Long pendingId) {

        PendingInstitution pending = pendingInstitutionRepository.findById(pendingId)
            .orElseThrow(() -> new ResourceNotFoundException("Pending institution not found"));

        String email = pending.getAdminEmail();
        String name = pending.getName();

        // Just delete — no trace kept
        pendingInstitutionRepository.delete(pending);

        // Email admin — rejected
        // emailService.sendInstitutionRejected(email, name);
    }
}
