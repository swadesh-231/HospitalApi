package com.hospitalapi.service.impl;

import com.hospitalapi.dto.CreatePatientRequest;
import com.hospitalapi.dto.PatientResponse;
import com.hospitalapi.entity.Insurance;
import com.hospitalapi.entity.Patient;
import com.hospitalapi.exception.BadRequestException;
import com.hospitalapi.exception.ResourceNotFoundException;
import com.hospitalapi.repository.InsuranceRepository;
import com.hospitalapi.repository.PatientRepository;
import com.hospitalapi.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse createPatient(CreatePatientRequest request) {
        Patient patient = Patient.builder()
                .name(request.name())
                .email(request.email())
                .birthDate(request.birthDate())
                .gender(request.gender())
                .bloodGroup(request.bloodGroup())
                .build();

        Patient saved = patientRepository.save(patient);
        return mapToResponse(saved);
    }

    @Override
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PatientResponse updatePatient(Long id, CreatePatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        patient.setName(request.name());
        patient.setBirthDate(request.birthDate());
        patient.setGender(request.gender());
        patient.setBloodGroup(request.bloodGroup());

        return mapToResponse(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getCreatedAt()
        );
    }
}
