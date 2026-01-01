package com.hospitalapi.repository;

import com.hospitalapi.entity.Appointment;
import com.hospitalapi.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    @Query("""
        SELECT a FROM Appointment a
        WHERE (:doctorId IS NULL OR a.doctor.id = :doctorId)
          AND (:status IS NULL OR a.status = :status)
          AND (:date IS NULL OR DATE(a.appointmentTime) = :date)
    """)
    List<Appointment> filter(
            @Param("doctorId") Long doctorId,
            @Param("status") AppointmentStatus status,
            @Param("date") LocalDate date);
}
