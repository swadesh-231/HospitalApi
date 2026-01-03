package com.hospitalapi.controller;

import com.hospitalapi.dto.*;
import com.hospitalapi.service.DepartmentService;
import com.hospitalapi.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    // Doctor Management
    @PostMapping("/doctors")
    public ResponseEntity<DoctorResponse> addDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    @GetMapping("/doctors")
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @GetMapping("/doctors/department/{departmentId}")
    public ResponseEntity<Page<DoctorResponse>> getDoctorsByDepartment(
            @PathVariable Long departmentId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(doctorService.getDoctorsByDepartment(departmentId, pageable));
    }

    // Department Management
    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    @GetMapping("/departments")
    public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(departmentService.getAllDepartments(pageable));
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PatchMapping("/departments/{departmentId}/head-doctor/{doctorId}")
    public ResponseEntity<Void> assignHeadDoctor(
            @PathVariable Long departmentId,
            @PathVariable Long doctorId) {
        departmentService.assignHeadDoctor(departmentId, doctorId);
        return ResponseEntity.ok().build();
    }
}
