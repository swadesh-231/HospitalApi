package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.BloodGroupType;
import com.hospitalapi.entity.enums.Gender;

import java.time.LocalDate;

public record CreatePatientRequest(
    String name,
    LocalDate birthDate,
    String email,
    Gender gender,
    BloodGroupType bloodGroup
) {}
