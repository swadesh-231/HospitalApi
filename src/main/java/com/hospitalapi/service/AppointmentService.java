package com.hospitalapi.service;

import com.hospitalapi.dto.AppointmentResponse;
import com.hospitalapi.dto.CreateAppointmentRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentResponse createAppointment(Long patientId, CreateAppointmentRequest request);

    AppointmentResponse getAppointment(Long id);

    List<AppointmentResponse> getAppointmentsByPatient(Long patientId);

    Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable);

    List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId);

    Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable);

    void cancelAppointment(Long patientId, Long appointmentId);

    void completeAppointment(Long doctorId, Long appointmentId);
}
