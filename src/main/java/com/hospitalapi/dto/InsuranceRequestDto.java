package com.hospitalapi.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceRequestDto {
    @NotBlank(message = "Policy number is required")
    private String policyNumber;
    @NotBlank(message = "Provider is required")
    private String provider;
    @Future(message = "Insurance must be valid in the future")
    private LocalDate validUntil;
}
