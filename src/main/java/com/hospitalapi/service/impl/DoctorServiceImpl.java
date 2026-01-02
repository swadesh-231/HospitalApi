package com.hospitalapi.service.impl;

import com.hospitalapi.dto.CreateDoctorRequest;
import com.hospitalapi.dto.DoctorResponse;
import com.hospitalapi.entity.Department;
import com.hospitalapi.entity.Doctor;
import com.hospitalapi.repository.DepartmentRepository;
import com.hospitalapi.repository.DoctorRepository;
import com.hospitalapi.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public DoctorResponse createDoctor(CreateDoctorRequest request) {
        // Real-world check: email must be unique
        doctorRepository.findByEmail(request.email())
                .ifPresent(d -> {
                    throw new IllegalStateException("Doctor with this email already exists");
                });

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Doctor doctor = Doctor.builder()
                .name(request.name())
                .specialization(request.specialization())
                .email(request.email())
                .department(department)
                .build();

        return mapToResponse(doctorRepository.save(doctor));
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        return mapToResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctorsByDepartment(Long departmentId) {
        // Verify department exists
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found");
        }

        return doctorRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getDepartment().getName());
    }
}
