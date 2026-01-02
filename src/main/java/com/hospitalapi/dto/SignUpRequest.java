package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.RoleType;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record SignUpRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String name,
        Set<RoleType> roles
) {
    public SignUpRequest {
        roles = (roles == null) ? Set.of() : Set.copyOf(roles);
    }
}
