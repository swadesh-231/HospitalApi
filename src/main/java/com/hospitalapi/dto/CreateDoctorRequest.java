package com.hospitalapi.dto;

public record CreateDoctorRequest(
    String name,
    String specialization,
    String email,
    Long departmentId
) {}
