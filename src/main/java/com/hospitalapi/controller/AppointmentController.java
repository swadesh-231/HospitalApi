package com.hospitalapi.controller;

import com.hospitalapi.dto.AppointmentResponse;
import com.hospitalapi.dto.CreateAppointmentRequest;
import com.hospitalapi.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(
            @Valid @RequestBody CreateAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<AppointmentResponse>> byPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByPatient(patientId));
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>> byDoctor(
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDoctor(doctorId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return ResponseEntity.ok().build();
    }
}
