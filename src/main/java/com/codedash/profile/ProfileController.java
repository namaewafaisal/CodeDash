package com.codedash.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.codedash.profile.dto.ProfileRequest;
import com.codedash.profile.dto.ProfileResponse;
import com.codedash.profile.dto.UpdateProfileRequest;
import com.codedash.security.dto.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // ---------------- CREATE OWN PROFILE ----------------
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> createProfile(
            @Valid @RequestBody ProfileRequest request,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        profileService.createProfile(userId, request);

        return ResponseEntity.ok("Profile created");
    }

    // ---------------- UPDATE OWN PROFILE ----------------
    @PatchMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        profileService.updateProfile(userId, request);

        return ResponseEntity.ok("Profile updated");
    }

    // ---------------- GET OWN PROFILE ----------------
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ProfileResponse getMyProfile(
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return profileService.getProfile(userId);
    }

    // ---------------- ADMIN / STAFF ----------------
    @GetMapping
    // @PreAuthorize("hasAnyRole('INSTITUTION_ADMIN', 'STAFF')")
    public List<ProfileResponse> getAll() {

        return profileService.getAll();
    }

    // ---------------- helper ----------------
    private UUID getUserId(Authentication authentication) {

        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        return userPrincipal.getUserId();
    }
}