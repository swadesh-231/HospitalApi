package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    LocalDateTime appointmentTime,
    AppointmentStatus status,
    String patientName,
    String doctorName
) {}
