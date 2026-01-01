package com.hospitalapi.service.impl;

import com.hospitalapi.dto.InsuranceRequestDto;
import com.hospitalapi.dto.PatientRequestDto;
import com.hospitalapi.dto.PatientResponseDto;
import com.hospitalapi.entity.Insurance;
import com.hospitalapi.entity.Patient;
import com.hospitalapi.exception.BadRequestException;
import com.hospitalapi.exception.ResourceNotFoundException;
import com.hospitalapi.repository.InsuranceRepository;
import com.hospitalapi.repository.PatientRepository;
import com.hospitalapi.service.PatientService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final InsuranceRepository insuranceRepository;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        Patient patient = modelMapper.map(request, Patient.class);
        Patient saved = patientRepository.save(patient);
        return modelMapper.map(saved, PatientResponseDto.class);
    }
    @Override
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId)
                );

        return modelMapper.map(patient, PatientResponseDto.class);
    }
    @Override
    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PatientResponseDto.class))
                .toList();
    }
    @Override
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId)
                );

        modelMapper.map(request, patient); // now skips nulls

        Patient updatedPatient = patientRepository.save(patient);

        return modelMapper.map(updatedPatient, PatientResponseDto.class);
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId)
                );
        patientRepository.delete(patient);
    }

    @Override
    public PatientResponseDto addInsurance(Long patientId, InsuranceRequestDto request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId)
                );
        if (patient.getInsurance() != null) {
            throw new BadRequestException("Patient already has insurance");
        }
        if (insuranceRepository.existsByPolicyNumber(request.getPolicyNumber())) {
            throw new BadRequestException("Insurance policy number already exists");
        }
        Insurance insurance = modelMapper.map(request, Insurance.class);
        patient.setInsurance(insurance); // owning side ONLY

        return modelMapper.map(patient, PatientResponseDto.class);
    }
}
