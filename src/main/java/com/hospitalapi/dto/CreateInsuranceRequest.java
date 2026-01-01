package com.hospitalapi.dto;

import java.time.LocalDate;

public record CreateInsuranceRequest(
    String policyNumber,
    String provider,
    LocalDate validUntil
) {}
