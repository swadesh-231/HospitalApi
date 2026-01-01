package com.hospitalapi.service;

import com.hospitalapi.dto.InsuranceRequestDto;
import com.hospitalapi.dto.PatientRequestDto;
import com.hospitalapi.dto.PatientResponseDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto request);
    PatientResponseDto getPatientById(Long patientId);
    List<PatientResponseDto> getAllPatients();
    PatientResponseDto updatePatient(Long patientId, PatientRequestDto request);
    void deletePatient(Long patientId);
    PatientResponseDto addInsurance(Long patientId, InsuranceRequestDto request);
}
