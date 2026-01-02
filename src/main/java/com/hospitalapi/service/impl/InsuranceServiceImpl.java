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

        patient.setInsurance(insurance); // owning side

        Patient savedPatient = patientRepository.save(patient);
        Insurance savedInsurance = savedPatient.getInsurance();
        return mapToResponse(savedInsurance);
    }

    @Override
    public InsuranceResponse getInsuranceByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Insurance insurance = patient.getInsurance();
        if (insurance == null) {
            throw new EntityNotFoundException("Insurance not found");
        }

        return mapToResponse(insurance);
    }

    @Override
    public void removeInsurance(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setInsurance(null); // orphanRemoval = true
    }

    private InsuranceResponse mapToResponse(Insurance insurance) {
        return new InsuranceResponse(
                insurance.getId(),
                insurance.getPolicyNumber(),
                insurance.getProvider(),
                insurance.getValidUntil()
        );
    }
}
