package com.hospitalapi.service.impl;

import com.hospitalapi.dto.DepartmentRequestDto;
import com.hospitalapi.dto.DepartmentResponseDto;
import com.hospitalapi.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto request) {
        return null;
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long departmentId) {
        return null;
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return List.of();
    }

    @Override
    public DepartmentResponseDto assignHeadDoctor(Long departmentId, Long doctorId) {
        return null;
    }

    @Override
    public DepartmentResponseDto addDoctorToDepartment(Long departmentId, Long doctorId) {
        return null;
    }

    @Override
    public DepartmentResponseDto removeDoctorFromDepartment(Long departmentId, Long doctorId) {
        return null;
    }
}
