package com.codedash.auth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedash.auth.dto.AuthRequest;
import com.codedash.auth.dto.AuthResponse;
import com.codedash.auth.dto.RegisterRequest;
import com.codedash.email.EmailService;
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
import com.codedash.security.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @Value("${app.backend-url}")
    private String backendUrl;

    // Older version
    // public void register(RegisterRequest request) {

    //     // 1. Find institution
    //     Institution institution = institutionRepository.findById(request.getInstitutionId())
    //         .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

    //     // 2. Email domain must match institution domain
    //     String emailDomain = request.getEmail().split("@")[1];
    //     if (!emailDomain.equalsIgnoreCase(institution.getDomain())) {
    //         throw new BadRequestException("Email domain does not match institution");
    //     }

    //     if (userRepository.findByEmail(request.getEmail()).isPresent()){
    //         throw new AlreadyExistsException("Email already registered");
    //     }

    //     Optional<PendingUser> existingUser = pendingUserRepository.findByEmail(request.getEmail());
        
    //     if (existingUser.isPresent()) {
    //         PendingUser pendingUser = existingUser.get();
    //         // not verified → check expiry
    //         if (pendingUser.getTokenExpiry() != null &&
    //             pendingUser.getTokenExpiry().isAfter(LocalDateTime.now())) {
    //             throw new BadRequestException("Verification link already sent.");
    //         }

    //         // token expired → re-register (update user)
    //         pendingUser.setPassword(passwordEncoder.encode(request.getPassword()));

    //         String newToken = UUID.randomUUID().toString();
    //         pendingUser.setVerificationToken(newToken);
    //         pendingUser.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

    //         pendingUserRepository.save(pendingUser);

    //         // Email is sent below
    //         try {
    //             System.out.println("Before mail");

    //             String verificationLink =
    //                     backendUrl
    //                     + "/api/auth/verify?token="
    //                     + pendingUser.getVerificationToken();
                
    //             emailService.sendVerificationEmail(
    //                     pendingUser.getEmail(),
    //                     verificationLink
    //             );
            
    //             System.out.println("After mail");
            
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             throw e;
    //         }

    //         return;
    //     }

    //     // 5. Create new user
    //     String verificationToken = UUID.randomUUID().toString();

    //     PendingUser pendingUser = new PendingUser();
    //     pendingUser.setEmail(request.getEmail());
    //     pendingUser.setPassword(passwordEncoder.encode(request.getPassword()));
    //     pendingUser.setInstitution(institution);
    //     pendingUser.setVerificationToken(verificationToken);
    //     pendingUser.setTokenExpiry(LocalDateTime.now().plusMinutes(10));

    //     pendingUserRepository.save(pendingUser);

    //     // email
    //     try {
    //         System.out.println("Before mail");

    //         String verificationLink =
    //                 backendUrl
    //                 + "/api/auth/verify?token="
    //                 + pendingUser.getVerificationToken();
            
    //         emailService.sendVerificationEmail(
    //                 pendingUser.getEmail(),
    //                 verificationLink
    //         );
        
    //         System.out.println("After mail");
        
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }
    // 

    public void register(RegisterRequest request) {

        // 1. Find institution
        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

        // 2. Email domain must match institution domain
        String emailDomain = request.getEmail().split("@")[1];
        if (!emailDomain.equalsIgnoreCase(institution.getDomain())) {
            throw new BadRequestException("Email domain does not match institution");
        }
        // 3. Email should be new
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already registered");
        }        

        // 4. Create new user

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        user.setInstitution(institution);

        userRepository.save(user);

    }

    // public AuthResponse login(AuthRequest request) {

    //     Optional<PendingUser> pending = pendingUserRepository.findByEmail(request.getEmail());

    //     if (pending.isPresent()) {
    //         if (pending.get().getTokenExpiry().isAfter(LocalDateTime.now())) {
    //             throw new BadRequestException("Verify your email first");
    //         } else {
    //             throw new BadRequestException("Verification expired. Please register again");
    //         }
    //     }

    //     User user = userRepository.findByEmail(request.getEmail())
    //         .orElseThrow(() -> new BadRequestException("Invalid credentials"));

    //     // check password
    //     if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    //         throw new BadRequestException("Invalid credentials");
    //     }

    //     String token = jwtUtil.generateToken(user);
    //     return new AuthResponse(
    //         token,
    //         user.getId(),
    //         user.getEmail(),
    //         user.getRole().name()
    //     );
    // }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(
            token,
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
