package com.hospitalapi.service.impl;

import com.hospitalapi.dto.AppointmentResponse;
import com.hospitalapi.dto.CreateAppointmentRequest;
import com.hospitalapi.entity.Appointment;
import com.hospitalapi.entity.Doctor;
import com.hospitalapi.entity.Patient;
import com.hospitalapi.entity.enums.AppointmentStatus;
import com.hospitalapi.repository.AppointmentRepository;
import com.hospitalapi.repository.DoctorRepository;
import com.hospitalapi.repository.PatientRepository;
import com.hospitalapi.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(request.appointmentTime())
                .reason(request.reason())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = getEntity(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Override
    public void completeAppointment(Long id) {
        Appointment appointment = getEntity(id);
        appointment.setStatus(AppointmentStatus.COMPLETED);
    }

    private Appointment getEntity(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName()
        );
    }
}
