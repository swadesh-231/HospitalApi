package com.hospitalapi.controller;

import com.hospitalapi.dto.CreateInsuranceRequest;
import com.hospitalapi.dto.InsuranceResponse;
import com.hospitalapi.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients/{patientId}/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceResponse> addInsurance(
            @PathVariable Long patientId,
            @Valid @RequestBody CreateInsuranceRequest request) {
        return ResponseEntity.ok(insuranceService.addInsurance(patientId, request));
    }

    @GetMapping
    public ResponseEntity<InsuranceResponse> getInsurance(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                insuranceService.getInsuranceByPatient(patientId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeInsurance(
            @PathVariable Long patientId) {
        insuranceService.removeInsurance(patientId);
        return ResponseEntity.noContent().build();
    }
}
