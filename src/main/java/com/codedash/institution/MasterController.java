package com.codedash.institution;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
@Tag(
    name = "Master",
    description = "Platform owner endpoints for institution approval and management"
)
@SecurityRequirement(name = "Bearer Auth")
public class MasterController {

    private final InstitutionService institutionService;

    @GetMapping("/institutions")
    @Operation(
        summary = "Get all institutions",
        description = "Returns all institution registration requests."
    )
    public List<Institution> getPending() {
        return institutionService.getAll();
    }

    @PostMapping("/institutions/{id}/approve")
    @Operation(
        summary = "Approve institution",
        description = "Approves an institution registration request."
    )
    public ResponseEntity<String> approve(@PathVariable Long id) {

        institutionService.approve(id);

        return ResponseEntity.ok("Institution approved");
    }

    @PostMapping("/institutions/{id}/reject")
    @Operation(
        summary = "Reject institution",
        description = "Rejects an institution registration request."
    )
    public ResponseEntity<String> reject(@PathVariable Long id) {

        institutionService.reject(id);

        return ResponseEntity.ok("Institution rejected");
    }
}