package com.hospitalapi.service.impl;

import com.hospitalapi.dto.CreatePatientRequest;
import com.hospitalapi.dto.PatientResponse;
import com.hospitalapi.entity.Patient;
import com.hospitalapi.repository.PatientRepository;
import com.hospitalapi.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse createPatient(CreatePatientRequest request) {
        patientRepository.findByEmail(request.email())
                .ifPresent(p -> {
                    throw new IllegalStateException("Patient with this email already exists");
                });

        Patient patient = Patient.builder()
                .name(request.name())
                .email(request.email())
                .birthDate(request.birthDate())
                .gender(request.gender())
                .bloodGroup(request.bloodGroup())
                .build();

        return mapToResponse(patientRepository.save(patient));
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        return mapToResponse(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getCreatedAt());
    }
}
