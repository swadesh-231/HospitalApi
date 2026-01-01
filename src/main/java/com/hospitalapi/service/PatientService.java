package com.hospitalapi.service;

import com.hospitalapi.dto.CreatePatientRequest;
import com.hospitalapi.dto.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(CreatePatientRequest request);
    PatientResponse getPatientById(Long id);
    List<PatientResponse> getAllPatients();
    PatientResponse updatePatient(Long id, CreatePatientRequest request);
    void deletePatient(Long id);
}
