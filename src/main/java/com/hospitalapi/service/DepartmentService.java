package com.hospitalapi.service;

import com.hospitalapi.dto.CreateDepartmentRequest;
import com.hospitalapi.dto.DepartmentResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    Page<DepartmentResponse> getAllDepartments(Pageable pageable);

    DepartmentResponse getDepartmentById(Long id);

    void assignHeadDoctor(Long departmentId, Long doctorId);
}
