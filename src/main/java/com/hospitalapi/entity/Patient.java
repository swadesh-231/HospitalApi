package com.hospitalapi.entity;

import com.hospitalapi.entity.enums.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40)
    private String name;
    private LocalDate birthDate;
    @Column(unique = true, nullable = false)
    private String email;
    private String gender;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance; //owning side

    @OneToMany(mappedBy = "patient") //inverse side
    private List<Appointment> appointments = new ArrayList<>();

}
