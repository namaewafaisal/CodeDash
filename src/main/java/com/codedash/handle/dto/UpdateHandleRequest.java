package com.codedash.handle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateHandleRequest {

    @NotBlank
    private String username;
}