package com.hospitalapi.service;

import com.hospitalapi.dto.AppointmentRequestDto;
import com.hospitalapi.dto.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto request);
    AppointmentResponseDto getAppointmentById(Long appointmentId);
    List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId);
    List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId);
    void cancelAppointment(Long appointmentId);
}
