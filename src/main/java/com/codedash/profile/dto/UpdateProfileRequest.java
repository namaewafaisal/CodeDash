package com.codedash.profile.dto;

import java.time.LocalDate;

import com.codedash.profile.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @Size(min = 1, message = "Full name cannot be blank")
    private String fullName;

    @Size(min = 1, message = "Register number cannot be blank")
    private String registerNumber;

    @Size(min = 1, message = "Department cannot be blank")
    private String department;

    private String section;

    @Min(value = 2020, message = "Graduation year seems too old")
    @Max(value = 2035, message = "Graduation year seems too far")
    private Integer graduationYear;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String trainingBatch;

    @Size(min = 10, max = 15, message = "Enter a valid phone number")
    private String phone;

    @Email(message = "Enter a valid email")
    private String personalEmail;
}