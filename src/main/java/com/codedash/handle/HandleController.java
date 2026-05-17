package com.codedash.handle;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.codedash.handle.dto.*;
import com.codedash.security.dto.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/handles")
@RequiredArgsConstructor
public class HandleController {

    private final HandleService handleService;

    // ---------------- GET ALL ----------------
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<HandleResponse> getHandles(Authentication authentication) {

        UUID userId = getUserId(authentication);

        return handleService.getHandles(userId);
    }

    // ---------------- GET ONE ----------------
    @GetMapping("/{platform}")
    @PreAuthorize("hasRole('STUDENT')")
    public HandleResponse getHandle(
            @PathVariable Platform platform,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return handleService.getHandle(userId, platform);
    }

    // ---------------- CREATE ----------------
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> createHandle(
            @Valid @RequestBody HandleRequest request,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        handleService.createHandle(userId, request);

        return ResponseEntity.ok("Handle created");
    }

    // ---------------- UPDATE ----------------
    @PatchMapping("/{platform}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> updateHandle(
            @PathVariable Platform platform,
            @Valid @RequestBody UpdateHandleRequest request,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        handleService.updateHandle(userId, platform, request);

        return ResponseEntity.ok("Handle updated");
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{platform}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> deleteHandle(
            @PathVariable Platform platform,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        handleService.deleteHandle(userId, platform);

        return ResponseEntity.ok("Handle deleted");
    }

    // --------------- Admin ------------------    
    @PatchMapping("/frequency")
    @PreAuthorize("hasAnyRole('INSTITUTION_ADMIN', 'STAFF')")
    public ResponseEntity<String> bulkUpdateFetchFrequency(
            @Valid @RequestBody BulkUpdateFetchFrequencyRequest request
    ) {
    
        handleService.bulkUpdateFetchFrequency(request);
    
        return ResponseEntity.ok(
                "Fetch frequency updated"
        );
    }
    // ---------------- helper ----------------
    private UUID getUserId(Authentication authentication) {
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        return principal.getUserId();
    }
}