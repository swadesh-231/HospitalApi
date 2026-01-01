package com.hospitalapi.service;

import com.hospitalapi.dto.DepartmentRequestDto;
import com.hospitalapi.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto request);
    DepartmentResponseDto getDepartmentById(Long departmentId);
    List<DepartmentResponseDto> getAllDepartments();
    DepartmentResponseDto assignHeadDoctor(Long departmentId, Long doctorId);
    DepartmentResponseDto addDoctorToDepartment(Long departmentId, Long doctorId);
    DepartmentResponseDto removeDoctorFromDepartment(Long departmentId, Long doctorId);

}
