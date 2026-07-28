package com.codedash.profile.dto;

import java.time.LocalDate;

import com.codedash.profile.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request to create a student profile")
public class ProfileRequest {

    @Schema(description = "Student's full name", example = "Mohamed Faizal")
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Schema(description = "Institution register number", example = "814723104089")
    @NotBlank(message = "Register number is required")
    private String registerNumber;

    @Schema(description = "Department name", example = "Computer Science and Engineering")
    @NotBlank(message = "Department is required")
    private String department;

    @Schema(description = "Class section", example = "A")
    private String section;

    @Schema(description = "Expected graduation year", example = "2027")
    @NotNull(message = "Graduation year is required")
    @Min(value = 2020, message = "Graduation year seems too old")
    @Max(value = 2035, message = "Graduation year seems too far")
    private Integer graduationYear;

    @Schema(description = "Student gender", example = "MALE")
    @NotNull(message = "Gender is required")
    private Gender gender;

    @Schema(description = "Date of birth", example = "2005-08-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Training batch", example = "Placement Batch 2027")
    private String trainingBatch;

    @Schema(description = "Phone number", example = "9876543210")
    @Size(min = 10, max = 15, message = "Enter a valid phone number")
    private String phone;

    @Schema(description = "Personal email address", example = "faizal@gmail.com")
    @Email(message = "Enter a valid email")
    private String personalEmail;
}