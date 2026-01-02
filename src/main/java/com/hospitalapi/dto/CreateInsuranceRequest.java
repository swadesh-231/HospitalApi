package com.hospitalapi.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateInsuranceRequest(
        @NotBlank(message = "Policy number is required") String policyNumber,

        @NotBlank(message = "Provider is required") String provider,

        @NotNull(message = "Valid until date is required") @Future(message = "Valid until date must be in the future") LocalDate validUntil) {
}
