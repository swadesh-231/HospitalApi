package com.hospitalapi.service;

import com.hospitalapi.dto.CreateDepartmentRequest;
import com.hospitalapi.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(CreateDepartmentRequest request);
    List<DepartmentResponse> getAllDepartments();
    DepartmentResponse getDepartmentById(Long id);
    void assignHeadDoctor(Long departmentId, Long doctorId);

}
