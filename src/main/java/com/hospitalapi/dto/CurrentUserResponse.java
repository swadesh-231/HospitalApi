package com.hospitalapi.dto;

import com.hospitalapi.entity.enums.RoleType;

import java.util.Set;

public record CurrentUserResponse(
        Long id,
        String username,
        String email,
        Set<RoleType> roles) {
}
