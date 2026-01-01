package com.hospitalapi.repository;

import com.hospitalapi.entity.Patient;
import com.hospitalapi.entity.enums.BloodGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByName(String name);
    @Query("""
        SELECT p
        FROM Patient p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Patient> searchByName(@Param("name") String name);

    @Query("""
        SELECT p
        FROM Patient p
        WHERE p.birthDate = :dob
    """)
    List<Patient> searchByBirthDate(@Param("dob") LocalDate dob);

    @Query("""
        SELECT p
        FROM Patient p
        WHERE p.bloodGroup = :bloodGroup
    """)
    List<Patient> searchByBloodGroup(
            @Param("bloodGroup") BloodGroupType bloodGroup
    );

    @Query("""
        SELECT p
        FROM Patient p
        WHERE p.email = :email
    """)
    Patient searchByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
}
