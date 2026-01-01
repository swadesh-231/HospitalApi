package com.hospitalapi.controller;

import com.hospitalapi.dto.InsuranceRequestDto;
import com.hospitalapi.dto.PatientRequestDto;
import com.hospitalapi.dto.PatientResponseDto;
import com.hospitalapi.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto request) {
        PatientResponseDto response = patientService.createPatient(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long patientId, @Valid @RequestBody PatientRequestDto request) {
        return ResponseEntity.ok(patientService.updatePatient(patientId, request));
    }
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{patientId}/insurance")
    public ResponseEntity<PatientResponseDto> addInsurance(@PathVariable Long patientId, @Valid @RequestBody InsuranceRequestDto request) {
        return ResponseEntity.ok(patientService.addInsurance(patientId, request));
    }
}
