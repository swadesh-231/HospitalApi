package com.hospitalapi.dto;

public record DoctorResponse(
    Long id,
    String name,
    String specialization,
    String email,
    String departmentName
) {}
