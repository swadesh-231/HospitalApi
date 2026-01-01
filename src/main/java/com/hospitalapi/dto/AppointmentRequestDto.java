package com.hospitalapi.dto;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequestDto {
    private LocalDateTime appointmentTime;
    private String reason;
    private Long patientId;
    private Long doctorId;
}
