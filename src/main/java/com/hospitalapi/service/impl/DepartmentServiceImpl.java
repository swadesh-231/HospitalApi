package com.hospitalapi.service.impl;

import com.hospitalapi.dto.CreateDepartmentRequest;
import com.hospitalapi.dto.DepartmentResponse;
import com.hospitalapi.entity.Department;
import com.hospitalapi.entity.Doctor;
import com.hospitalapi.repository.DepartmentRepository;
import com.hospitalapi.repository.DoctorRepository;
import com.hospitalapi.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        // rule: department name must be unique
        departmentRepository.findByName(request.name())
                .ifPresent(d -> {
                    throw new IllegalStateException("Department already exists");
                });

        Department department = Department.builder()
                .name(request.name())
                .build();

        Department saved = departmentRepository.save(department);
        return mapToResponse(saved);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<DepartmentResponse> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        return mapToResponse(department);
    }

    @Override
    public void assignHeadDoctor(Long departmentId, Long doctorId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        // critical rule: head doctor must belong to same department
        if (!doctor.getDepartment().getId().equals(departmentId)) {
            throw new IllegalStateException(
                    "Doctor does not belong to this department");
        }
        department.setHeadDoctor(doctor);
    }

    private DepartmentResponse mapToResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getHeadDoctor() != null
                        ? department.getHeadDoctor().getName()
                        : null);
    }
}
