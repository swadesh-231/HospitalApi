package com.hospitalapi.repository;

import com.hospitalapi.entity.Patient;
import com.hospitalapi.entity.enums.BloodGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    @Query("""
        SELECT p FROM Patient p
        WHERE (:name IS NULL OR p.name LIKE %:name%)
          AND (:email IS NULL OR p.email = :email)
          AND (:bloodGroup IS NULL OR p.bloodGroup = :bloodGroup)
    """)
    List<Patient> search(
            @Param("name") String name,
            @Param("email") String email,
            @Param("bloodGroup") BloodGroupType bloodGroup);
}
