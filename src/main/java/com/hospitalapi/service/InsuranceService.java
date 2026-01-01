package com.hospitalapi.service;

import com.hospitalapi.dto.CreateInsuranceRequest;
import com.hospitalapi.dto.InsuranceResponse;

public interface InsuranceService {
    InsuranceResponse addInsurance(Long patientId, CreateInsuranceRequest request);
    InsuranceResponse getInsuranceByPatient(Long patientId);
    void removeInsurance(Long patientId);
}
