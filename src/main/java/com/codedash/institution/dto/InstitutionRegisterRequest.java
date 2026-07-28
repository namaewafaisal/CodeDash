package com.codedash.institution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Institution registration request")
public class InstitutionRegisterRequest {

    @Schema(
        description = "Official institution name",
        example = "SRM TRP Engineering College"
    )
    @NotBlank
    private String institutionName;

    @Schema(
        description = "Official institution email domain",
        example = "trp.srmtrichy.edu.in"
    )
    @NotBlank
    private String domain;

    @Schema(
        description = "Institution administrator email address",
        example = "admin@trp.srmtrichy.edu.in"
    )
    @Email
    @NotBlank
    private String adminEmail;

    @Schema(
        description = "Administrator account password",
        example = "SecurePass123"
    )
    @NotBlank
    @Size(min = 8, max = 64)
    private String adminPassword;
}