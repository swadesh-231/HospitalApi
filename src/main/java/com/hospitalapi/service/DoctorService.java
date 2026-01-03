package com.hospitalapi.service;

import com.hospitalapi.dto.CreateDoctorRequest;
import com.hospitalapi.dto.DoctorResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    DoctorResponse createDoctor(CreateDoctorRequest request);

    DoctorResponse getDoctorById(Long id);


    Page<DoctorResponse> getDoctorsByDepartment(Long departmentId, Pageable pageable);

    List<DoctorResponse> getAllDoctors();

    Page<DoctorResponse> getAllDoctors(Pageable pageable);
}
