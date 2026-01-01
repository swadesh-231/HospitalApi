package com.hospitalapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDto {
    private String name;
    private Long headDoctorId;
    private Set<Long> doctorIds;
}
