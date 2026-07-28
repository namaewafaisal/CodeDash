package com.codedash.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codedash.auth.dto.AuthRequest;
import com.codedash.auth.dto.AuthResponse;
import com.codedash.auth.dto.RegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(
    name = "Authentication",
    description = "User registration, login and email verification endpoints."
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Register a student account",
        description = "Registers a new student account for an approved institution. "
            + "The email domain must match the institution's registered domain."
    )
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return "Registration successful";
    }

    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user and returns a JWT access token."
    )
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Verify email address",
        description = "Verifies a user's email using the verification token."
    )
    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {

        authService.verify(token);
        return ResponseEntity.ok("Verified");
    }
}
