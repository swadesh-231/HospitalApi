package com.hospitalapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDoctorRequest(
        @NotBlank(message = "Name is required") String name,

        String specialization,

        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,

        @NotNull(message = "Department ID is required") Long departmentId) {
}
