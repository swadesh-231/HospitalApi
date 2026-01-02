package com.hospitalapi.security;

import com.hospitalapi.entity.enums.PermissionType;
import com.hospitalapi.entity.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class RolePermissionMapping {

    private static final Map<RoleType, Set<PermissionType>> ROLE_PERMISSIONS =
            new EnumMap<>(RoleType.class);

    static {
        ROLE_PERMISSIONS.put(
                RoleType.PATIENT,
                Set.of(
                        PermissionType.PATIENT_READ,
                        PermissionType.APPOINTMENT_READ,
                        PermissionType.APPOINTMENT_WRITE
                )
        );

        ROLE_PERMISSIONS.put(
                RoleType.DOCTOR,
                Set.of(
                        PermissionType.PATIENT_READ,
                        PermissionType.APPOINTMENT_READ,
                        PermissionType.APPOINTMENT_WRITE,
                        PermissionType.APPOINTMENT_DELETE
                )
        );

        ROLE_PERMISSIONS.put(
                RoleType.ADMIN,
                Set.of(
                        PermissionType.PATIENT_READ,
                        PermissionType.PATIENT_WRITE,
                        PermissionType.APPOINTMENT_READ,
                        PermissionType.APPOINTMENT_WRITE,
                        PermissionType.APPOINTMENT_DELETE,
                        PermissionType.USER_MANAGE,
                        PermissionType.REPORT_VIEW
                )
        );
    }

    private RolePermissionMapping() {
        // prevent instantiation
    }

    public static Set<GrantedAuthority> getAuthoritiesForRole(RoleType role) {
        return ROLE_PERMISSIONS.getOrDefault(role, Set.of())
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.asAuthority()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
