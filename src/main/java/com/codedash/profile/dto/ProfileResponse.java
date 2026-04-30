package com.codedash.profile.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.codedash.profile.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String fullName;
    private String registerNumber;
    private String department;
    private String section;
    private Integer graduationYear;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String trainingBatch;
    private String phone;
    private String personalEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}