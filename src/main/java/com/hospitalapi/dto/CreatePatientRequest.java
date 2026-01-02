package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.BloodGroupType;
import com.hospitalapi.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreatePatientRequest(
        @NotBlank(message = "Name is required") @Size(max = 40, message = "Name must be at most 40 characters") String name,

        @NotNull(message = "Birth date is required") LocalDate birthDate,

        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,

        @NotNull(message = "Gender is required") Gender gender,

        BloodGroupType bloodGroup) {
}
