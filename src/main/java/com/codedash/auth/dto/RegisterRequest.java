package com.codedash.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email 
    @NotBlank 
    private String email;
    
    @NotBlank 
    @Size(min = 8, max = 64) 
    private String password;
    
    @NotNull 
    private Long institutionId;
}