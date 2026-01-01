package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.BloodGroupType;
import com.hospitalapi.entity.enums.Gender;

import java.time.LocalDateTime;

public record PatientResponse(
    Long id,
    String name,
    String email,
    Gender gender,
    BloodGroupType bloodGroup,
    LocalDateTime createdAt
) {}
