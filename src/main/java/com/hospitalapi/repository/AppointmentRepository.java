package com.hospitalapi.repository;

import com.hospitalapi.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  List<Appointment> findByPatientId(Long patientId);

  List<Appointment> findByDoctorId(Long doctorId);

  @Query("""
          SELECT COUNT(a) > 0 FROM Appointment a
          WHERE a.doctor.id = :doctorId
            AND a.status = 'SCHEDULED'
            AND a.appointmentTime BETWEEN :startTime AND :endTime
      """)
  boolean existsConflictingAppointment(
      @Param("doctorId") Long doctorId,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);
}
