package com.hospitalapi.controller;

import com.hospitalapi.dto.CreateDepartmentRequest;
import com.hospitalapi.dto.DepartmentResponse;
import com.hospitalapi.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(
            @Valid @RequestBody CreateDepartmentRequest request) {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PatchMapping("/{id}/head-doctor/{doctorId}")
    public ResponseEntity<Void> assignHeadDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        departmentService.assignHeadDoctor(id, doctorId);
        return ResponseEntity.ok().build();
    }
}
