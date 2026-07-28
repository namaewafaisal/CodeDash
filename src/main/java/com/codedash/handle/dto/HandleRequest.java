package com.codedash.handle.dto;

import com.codedash.handle.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request to create a coding platform handle")
public class HandleRequest {

    @Schema(
        description = "Coding platform",
        example = "LEETCODE"
    )
    @NotNull
    private Platform platform;

    @Schema(
        description = "Username on the selected platform",
        example = "faizal123"
    )
    @NotBlank
    private String username;
}