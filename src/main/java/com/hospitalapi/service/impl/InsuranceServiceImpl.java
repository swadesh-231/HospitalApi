package com.hospitalapi.service.impl;

import com.hospitalapi.dto.CreateInsuranceRequest;
import com.hospitalapi.dto.InsuranceResponse;
import com.hospitalapi.entity.Insurance;
import com.hospitalapi.entity.Patient;
import com.hospitalapi.repository.PatientRepository;
import com.hospitalapi.service.InsuranceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InsuranceServiceImpl implements InsuranceService {

    private final PatientRepository patientRepository;

    @Override
    public InsuranceResponse addInsurance(Long patientId, CreateInsuranceRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        if (patient.getInsurance() != null) {
            throw new IllegalStateException("Patient already has insurance");
        }

        Insurance insurance = Insurance.builder()
                .policyNumber(request.policyNumber())
                .provider(request.provider())
                .validUntil(request.validUntil())
                .build();

        patient.setInsurance(insurance);
        Patient savedPatient = patientRepository.save(patient);
        return mapToResponse(savedPatient.getInsurance());
    }

    @Override
    @Transactional(readOnly = true)
    public InsuranceResponse getInsuranceByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Insurance insurance = patient.getInsurance();
        if (insurance == null) {
            throw new EntityNotFoundException("Insurance not found");
        }

        return mapToResponse(insurance);
    }

    private InsuranceResponse mapToResponse(Insurance insurance) {
        return new InsuranceResponse(
                insurance.getId(),
                insurance.getPolicyNumber(),
                insurance.getProvider(),
                insurance.getValidUntil());
    }
}
