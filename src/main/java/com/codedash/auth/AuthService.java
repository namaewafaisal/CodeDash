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

    public void register(RegisterRequest request) {

        // 1. Find institution
        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

        // 2. Institution must be APPROVED
        if (institution.getStatus() != InstitutionStatus.APPROVED) {
            throw new BadRequestException("Institution is not active");
        }

        // 3. Email domain must match institution domain
        String emailDomain = request.getEmail().split("@")[1];
        if (!emailDomain.equalsIgnoreCase(institution.getDomain())) {
            throw new BadRequestException("Email domain does not match institution");
        }

        // 4. Check existing user
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // already verified → block
            if (existingUser.isEmailVerified()) {
                throw new AlreadyExistsException("Email already registered");
            }

            // not verified → check expiry
            if (existingUser.getTokenExpiry() != null &&
                existingUser.getTokenExpiry().isAfter(LocalDateTime.now())) {
                throw new BadRequestException("Verification link already sent. Try later.");
            }

            // token expired → re-register (update user)
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));

            String newToken = UUID.randomUUID().toString();
            existingUser.setVerificationToken(newToken);
            existingUser.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

            userRepository.save(existingUser);
            return;
        }

        // 5. Create new user
        String verificationToken = UUID.randomUUID().toString();

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        user.setInstitution(institution);
        user.setEmailVerified(false);
        user.setVerificationToken(verificationToken);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        // check verified
        if (!user.isEmailVerified()) {
            throw new BadRequestException("Please verify your email first");
        }

        // return basic response (no JWT yet)
        return new AuthResponse(
            user.getId(),
            user.getEmail(),
            user.getRole().name()
        );
    }
    public void verify(String token) {

        User user = userRepository.findByVerificationToken(token)
            .orElseThrow(() -> new BadRequestException("Invalid token"));

        // check expiry
        if (user.getTokenExpiry() != null &&
            user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expired");
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);
    }
}
