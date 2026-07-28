package com.codedash.handle;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.codedash.handle.dto.*;
import com.codedash.security.dto.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/handles")
@RequiredArgsConstructor
@Tag(
    name = "Handles",
    description = "Student coding platform handle management"
)
@SecurityRequirement(name = "Bearer Auth")
public class HandleController {

    private final HandleService handleService;

    // ---------------- GET ALL ----------------
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Get all handles",
        description = "Returns all coding platform handles for the authenticated student."
    )
    public List<HandleResponse> getHandles(Authentication authentication) {

        UUID userId = getUserId(authentication);

        return handleService.getHandles(userId);
    }

    // ---------------- GET ONE ----------------
    @GetMapping("/{platform}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Get handle",
        description = "Returns the handle for the specified coding platform."
    )
    public HandleResponse getHandle(
            @PathVariable Platform platform,
            Authentication authentication) {

        UUID userId = getUserId(authentication);

        return handleService.getHandle(userId, platform);
    }

    // ---------------- CREATE ----------------
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
        summary = "Create handle",
        description = "Adds a coding platform handle for the authenticated student."
    )
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
    @Operation(
        summary = "Update handle",
        description = "Updates the username for a coding platform."
    )
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
    @Operation(
        summary = "Delete handle",
        description = "Deletes a coding platform handle."
    )
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
    @Operation(
        summary = "Bulk update fetch frequency",
        description = "Updates the fetch frequency for multiple users on a coding platform."
    )
    public ResponseEntity<String> bulkUpdateFetchFrequency(
            @Valid @RequestBody BulkUpdateFetchFrequencyRequest request) {

        handleService.bulkUpdateFetchFrequency(request);

        return ResponseEntity.ok("Fetch frequency updated");
    }

    // ---------------- helper ----------------
    private UUID getUserId(Authentication authentication) {
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        return principal.getUserId();
    }
}