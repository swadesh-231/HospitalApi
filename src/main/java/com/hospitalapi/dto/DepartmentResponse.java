package com.hospitalapi.dto;

public record DepartmentResponse(
        Long id,
        String name,
        String headDoctorName
) {}
