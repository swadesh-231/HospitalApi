package com.hospitalapi.repository;

import com.hospitalapi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findByDepartmentId(Long departmentId, Pageable pageable);
    Optional<Doctor> findByEmail(String email);
}
