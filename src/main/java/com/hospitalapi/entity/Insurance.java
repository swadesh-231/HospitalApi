package com.hospitalapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String policyNumber;
    private String provider;
    @Column(nullable = false)
    private LocalDate validUntil;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;
    @OneToOne(mappedBy = "insurance")
    private Patient patient;

}
