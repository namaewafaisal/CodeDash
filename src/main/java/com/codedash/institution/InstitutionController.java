package com.codedash.institution;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.institution.dto.InstitutionRegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
@Tag(
    name = "Institutions",
    description = "Institution registration endpoints"
)
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Register an institution",
        description = "Submits a new institution registration request for approval."
    )
    public ResponseEntity<String> register(
            @Valid @RequestBody InstitutionRegisterRequest request) {

        institutionService.registerInstitution(request);

        return ResponseEntity.status(201).body("Registration request submitted");
    }
}