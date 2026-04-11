package com.codedash.auth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedash.auth.dto.AuthRequest;
import com.codedash.auth.dto.AuthResponse;
import com.codedash.auth.dto.RegisterRequest;
import com.codedash.exceptionhandlers.AlreadyExistsException;
import com.codedash.exceptionhandlers.BadRequestException;
import com.codedash.exceptionhandlers.ResourceNotFoundException;
import com.codedash.institution.Institution;
import com.codedash.institution.InstitutionRepository;
import com.codedash.institution.InstitutionStatus;
import com.codedash.registration.PendingInstitution;
import com.codedash.registration.PendingInstitutionRepository;
import com.codedash.registration.PendingUser;
import com.codedash.registration.PendingUserRepository;
import com.codedash.user.Role;
import com.codedash.user.User;
import com.codedash.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final PendingUserRepository pendingUserRepository;


    public void register(RegisterRequest request) {

        // 1. Find institution
        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

        // 2. Email domain must match institution domain
        String emailDomain = request.getEmail().split("@")[1];
        if (!emailDomain.equalsIgnoreCase(institution.getDomain())) {
            throw new BadRequestException("Email domain does not match institution");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already registered");
        }

        Optional<PendingUser> existingUser = pendingUserRepository.findByEmail(request.getEmail());
        
        if (existingUser.isPresent()) {
            PendingUser user = existingUser.get();
            // not verified → check expiry
            if (user.getTokenExpiry() != null &&
                user.getTokenExpiry().isAfter(LocalDateTime.now())) {
                throw new BadRequestException("Verification link already sent.");
            }

            // token expired → re-register (update user)
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            String newToken = UUID.randomUUID().toString();
            user.setVerificationToken(newToken);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

            pendingUserRepository.save(user);

            // Email is sent below

            return;
        }

        // 5. Create new user
        String verificationToken = UUID.randomUUID().toString();

        PendingUser pendingUser = new PendingUser();
        pendingUser.setEmail(request.getEmail());
        pendingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        pendingUser.setInstitution(institution);
        pendingUser.setVerificationToken(verificationToken);
        pendingUser.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

        pendingUserRepository.save(pendingUser);
    }

    public AuthResponse login(AuthRequest request) {

        Optional<PendingUser> pending = pendingUserRepository.findByEmail(request.getEmail());

        if (pending.isPresent()) {
            if (pending.get().getTokenExpiry().isAfter(LocalDateTime.now())) {
                throw new BadRequestException("Verify your email first");
            } else {
                throw new BadRequestException("Verification expired. Please register again");
            }
        }

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        // return basic response (no JWT yet)
        return new AuthResponse(
            user.getId(),
            user.getEmail(),
            user.getRole().name()
        );
    }
    public void verify(String token) {

        PendingUser pendingUser = pendingUserRepository.findByVerificationToken(token)
            .orElseThrow(() -> new BadRequestException("Invalid token"));

        // check expiry
        if (pendingUser.getTokenExpiry() != null &&
            pendingUser.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expired");
        }

        User user = new User();
        user.setEmail(pendingUser.getEmail());
        user.setPassword(pendingUser.getPassword());
        user.setInstitution(pendingUser.getInstitution());
        user.setRole(Role.STUDENT);
        

        userRepository.save(user);
        pendingUserRepository.delete(pendingUser);
    }
}
