package com.hospitalapi.dto;

import java.time.LocalDate;

public record InsuranceResponse(
    Long id,
    String policyNumber,
    String provider,
    LocalDate validUntil
) {}
