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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private static final int APPOINTMENT_DURATION_MINUTES = 30;

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public AppointmentResponse createAppointment(Long patientId, CreateAppointmentRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        // Prevent double-booking
        LocalDateTime startTime = request.appointmentTime().minusMinutes(APPOINTMENT_DURATION_MINUTES);
        LocalDateTime endTime = request.appointmentTime().plusMinutes(APPOINTMENT_DURATION_MINUTES);

        if (appointmentRepository.existsConflictingAppointment(request.doctorId(), startTime, endTime)) {
            throw new IllegalStateException("Doctor is not available at this time");
        }

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
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Patient not found");
        }
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found");
        }
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void cancelAppointment(Long patientId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Validate patient owns this appointment
        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new IllegalStateException("This appointment does not belong to this patient");
        }

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled appointments can be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Override
    public void completeAppointment(Long doctorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Validate doctor owns this appointment
        if (!appointment.getDoctor().getId().equals(doctorId)) {
            throw new IllegalStateException("This appointment does not belong to this doctor");
        }

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled appointments can be completed");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName());
    }
}
