package com.codedash.institution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InstitutionRegisterRequest {
    @NotBlank 
    private String institutionName;
    
    @NotBlank 
    private String domain;          // must not be gmail.com, yahoo.com etc
    
    @Email 
    @NotBlank 
    private String adminEmail;
    
    @NotBlank 
    @Size(min=8, max=64) 
    private String adminPassword;
}