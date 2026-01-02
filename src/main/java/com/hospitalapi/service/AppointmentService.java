package com.hospitalapi.service;

import com.hospitalapi.dto.AppointmentResponse;
import com.hospitalapi.dto.CreateAppointmentRequest;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(Long patientId, CreateAppointmentRequest request);

    AppointmentResponse getAppointment(Long id);

    List<AppointmentResponse> getAppointmentsByPatient(Long patientId);

    List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId);

    void cancelAppointment(Long patientId, Long appointmentId);

    void completeAppointment(Long doctorId, Long appointmentId);
}
