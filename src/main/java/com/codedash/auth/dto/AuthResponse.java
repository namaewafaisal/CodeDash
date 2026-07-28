package com.codedash.auth.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Authentication response")
public class AuthResponse {

    @Schema(
        description = "JWT access token used to authenticate subsequent requests"
    )
    private String token;

    @Schema(
        description = "Unique identifier of the authenticated user",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID userId;

    @Schema(
        description = "Institution email address of the authenticated user",
        example = "student@trp.srmtrichy.edu.in"
    )
    private String email;

    @Schema(
        description = "Role assigned to the authenticated user",
        example = "STUDENT"
    )
    private String role;
}