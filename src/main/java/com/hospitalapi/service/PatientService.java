package com.hospitalapi.service;

import com.hospitalapi.dto.CreatePatientRequest;
import com.hospitalapi.dto.PatientResponse;

public interface PatientService {
    PatientResponse createPatient(CreatePatientRequest request);

    PatientResponse getPatientById(Long id);
}
