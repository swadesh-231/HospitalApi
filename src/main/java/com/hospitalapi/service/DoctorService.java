package com.hospitalapi.service;

import com.hospitalapi.dto.DoctorRequestDto;
import com.hospitalapi.dto.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    DoctorResponseDto createDoctor(DoctorRequestDto request);
    DoctorResponseDto getDoctorById(Long doctorId);
    List<DoctorResponseDto> getAllDoctors();
    DoctorResponseDto updateDoctor(Long doctorId, DoctorRequestDto request);
    void deleteDoctor(Long doctorId);
}
