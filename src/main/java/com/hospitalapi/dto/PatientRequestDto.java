package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.BloodGroupType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    @NotBlank(message = "Patient name is required")
    private String name;
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotNull(message = "Blood group is required")
    private BloodGroupType bloodGroup;
}