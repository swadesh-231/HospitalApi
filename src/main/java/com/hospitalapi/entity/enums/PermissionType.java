package com.hospitalapi.entity.enums;

public enum PermissionType {
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    APPOINTMENT_DELETE("appointment:delete"),
    USER_MANAGE("user:manage"),
    REPORT_VIEW("report:view");
    private final String value;
    PermissionType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String asAuthority() {
        return value;
    }
}
