package com.hospitalapi.service;

import com.hospitalapi.dto.CreateDoctorRequest;
import com.hospitalapi.dto.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(CreateDoctorRequest request);
    DoctorResponse getDoctorById(Long id);
    List<DoctorResponse> getDoctorsByDepartment(Long departmentId);
    List<DoctorResponse> getAllDoctors();
}
