package com.hospitalapi.exception;

import java.util.Map;

public final class ErrorMessages {
    private ErrorMessages() {}
    public static final Map<String, String> MESSAGES = Map.ofEntries(
        Map.entry("INTERNAL_ERROR", "Something went wrong. Please try again later."),
        Map.entry("VALIDATION_ERROR", "Invalid request data"),
        Map.entry("RESOURCE_NOT_FOUND", "Requested resource not found"),
        Map.entry("DUPLICATE_RESOURCE", "Resource already exists"),
        Map.entry("UNAUTHORIZED", "You are not authorized to perform this action"),
        Map.entry("ACCESS_DENIED", "Access is denied"),
        Map.entry("BAD_REQUEST", "Bad request"),
        Map.entry("METHOD_NOT_ALLOWED", "HTTP method not allowed"),
        Map.entry("DATA_INTEGRITY", "Data integrity violation"),
        Map.entry("TYPE_MISMATCH", "Invalid parameter type"),
        Map.entry("MISSING_PARAM", "Missing required request parameter")
    );
}
