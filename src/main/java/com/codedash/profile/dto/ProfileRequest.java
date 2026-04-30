package com.codedash.profile.dto;

import java.time.LocalDate;

import com.codedash.profile.Gender;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Register number is required")
    private String registerNumber;

    @NotBlank(message = "Department is required")
    private String department;

    private String section;

    @NotNull(message = "Graduation year is required")
    @Min(value = 2020, message = "Graduation year seems too old")
    @Max(value = 2035, message = "Graduation year seems too far")
    private Integer graduationYear;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private LocalDate dateOfBirth;

    private String trainingBatch;

    @Size(min = 10, max = 15, message = "Enter a valid phone number")
    private String phone;

    @Email(message = "Enter a valid email")
    private String personalEmail;
}