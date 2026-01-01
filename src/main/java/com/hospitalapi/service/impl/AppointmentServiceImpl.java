package com.hospitalapi.service.impl;

import com.hospitalapi.dto.AppointmentRequestDto;
import com.hospitalapi.dto.AppointmentResponseDto;
import com.hospitalapi.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto request) {
        return null;
    }

    @Override
    public AppointmentResponseDto getAppointmentById(Long appointmentId) {
        return null;
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId) {
        return List.of();
    }

    @Override
    public void cancelAppointment(Long appointmentId) {

    }
}
