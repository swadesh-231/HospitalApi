package com.hospitalapi.controller;

import com.hospitalapi.dto.*;
import com.hospitalapi.service.AppointmentService;
import com.hospitalapi.service.InsuranceService;
import com.hospitalapi.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<PatientResponse> register(@Valid @RequestBody CreatePatientRequest request) {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Appointments
    @PostMapping("/{patientId}/appointments")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @PathVariable Long patientId,
            @Valid @RequestBody CreateAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(patientId, request));
    }

    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<Page<AppointmentResponse>> getAppointments(
            @PathVariable Long patientId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId, pageable));
    }

    @PatchMapping("/{patientId}/appointments/{appointmentId}/cancel")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable Long patientId,
            @PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(patientId, appointmentId);
        return ResponseEntity.noContent().build();
    }

    // Insurance
    @PostMapping("/{patientId}/insurance")
    public ResponseEntity<InsuranceResponse> addInsurance(
            @PathVariable Long patientId,
            @Valid @RequestBody CreateInsuranceRequest request) {
        return ResponseEntity.ok(insuranceService.addInsurance(patientId, request));
    }

    @GetMapping("/{patientId}/insurance")
    public ResponseEntity<InsuranceResponse> getInsurance(@PathVariable Long patientId) {
        return ResponseEntity.ok(insuranceService.getInsuranceByPatient(patientId));
    }
}
