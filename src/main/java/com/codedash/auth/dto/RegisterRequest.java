package com.codedash.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Registration request")
public class RegisterRequest {

    @Schema(
        description = "Institution email address",
        example = "student@trp.srmtrichy.edu.in"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
        description = "Password between 8 and 64 characters",
        example = "SecurePass123"
    )
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @Schema(
        description = "Identifier of the institution the user belongs to",
        example = "1"
    )
    @NotNull
    private Long institutionId;
}