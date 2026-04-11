package com.codedash.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private UUID userId;
    private String email;
    private String role;
}
