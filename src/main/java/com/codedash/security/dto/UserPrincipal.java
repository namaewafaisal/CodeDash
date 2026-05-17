package com.codedash.security.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal {
    private UUID userId;
    private String role;
    private Long institutionId;
}