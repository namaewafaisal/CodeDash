package com.codedash.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class AuthRequest {

    @Schema(
        description = "Institution email address",
        example = "student@trp.srmtrichy.edu.in"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
        description = "Account password",
        example = "SecurePass123"
    )
    @NotBlank
    private String password;
}