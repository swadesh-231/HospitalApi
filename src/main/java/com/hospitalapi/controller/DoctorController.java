package com.hospitalapi.controller;

import com.hospitalapi.dto.AppointmentResponse;
import com.hospitalapi.dto.DoctorResponse;
import com.hospitalapi.service.AppointmentService;
import com.hospitalapi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }

    @PatchMapping("/{doctorId}/appointments/{appointmentId}/complete")
    public ResponseEntity<Void> completeAppointment(
            @PathVariable Long doctorId,
            @PathVariable Long appointmentId) {
        appointmentService.completeAppointment(doctorId, appointmentId);
        return ResponseEntity.ok().build();
    }
}
