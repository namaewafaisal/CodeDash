package com.codedash.handle.dto;

import com.codedash.handle.Platform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HandleRequest {

    @NotNull
    private Platform platform;

    @NotBlank
    private String username;
}