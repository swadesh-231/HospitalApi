package com.hospitalapi.dto;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(
    Long patientId,
    Long doctorId,
    LocalDateTime appointmentTime,
    String reason
) {}
