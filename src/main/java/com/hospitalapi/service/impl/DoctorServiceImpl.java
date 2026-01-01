package com.hospitalapi.service.impl;

import com.hospitalapi.dto.DoctorRequestDto;
import com.hospitalapi.dto.DoctorResponseDto;
import com.hospitalapi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    @Override
    public DoctorResponseDto createDoctor(DoctorRequestDto request) {
        return null;
    }

    @Override
    public DoctorResponseDto getDoctorById(Long doctorId) {
        return null;
    }

    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        return List.of();
    }

    @Override
    public DoctorResponseDto updateDoctor(Long doctorId, DoctorRequestDto request) {
        return null;
    }

    @Override
    public void deleteDoctor(Long doctorId) {

    }
}
