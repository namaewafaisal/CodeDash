package com.codedash.profile.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.codedash.profile.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Student profile")
public class ProfileResponse {

    @Schema(description = "Profile ID", example = "1")
    private Long id;

    @Schema(description = "Student's full name", example = "Mohamed Faizal")
    private String fullName;

    @Schema(description = "Institution register number", example = "814723104089")
    private String registerNumber;

    @Schema(description = "Department name", example = "Computer Science and Engineering")
    private String department;

    @Schema(description = "Class section", example = "A")
    private String section;

    @Schema(description = "Expected graduation year", example = "2027")
    private Integer graduationYear;

    @Schema(description = "Student gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Date of birth", example = "2005-08-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Training batch", example = "Placement Batch 2027")
    private String trainingBatch;

    @Schema(description = "Phone number", example = "9876543210")
    private String phone;

    @Schema(description = "Personal email address", example = "faizal@gmail.com")
    private String personalEmail;

    @Schema(description = "Profile creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last profile update timestamp")
    private LocalDateTime updatedAt;
}