package com.hospitalapi.controller;

import com.hospitalapi.dto.CreateInsuranceRequest;
import com.hospitalapi.dto.InsuranceResponse;
import com.hospitalapi.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientInsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping("/{patientId}/insurance")
    public ResponseEntity<InsuranceResponse> addInsurance(@PathVariable Long patientId,@Valid @RequestBody CreateInsuranceRequest request) {
        InsuranceResponse response = insuranceService.addInsurance(patientId, request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{patientId}/insurance")
    public ResponseEntity<InsuranceResponse> getInsurance(@PathVariable Long patientId) {
        return ResponseEntity.ok(insuranceService.getInsuranceByPatient(patientId));
    }
    @DeleteMapping("/{patientId}/insurance")
    public ResponseEntity<Void> removeInsurance(@PathVariable Long patientId) {
        insuranceService.removeInsurance(patientId);
        return ResponseEntity.noContent().build();
    }
}
