package com.codedash.handle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request to update a coding platform username")
public class UpdateHandleRequest {

    @Schema(
        description = "New username",
        example = "faizal_new"
    )
    @NotBlank
    private String username;
}