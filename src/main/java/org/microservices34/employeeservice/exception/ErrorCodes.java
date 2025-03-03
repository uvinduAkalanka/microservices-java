package org.microservices34.employeeservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodes {
    /* Resource Error Codes (1000-1999) */
    public static final String RESOURCE_NOT_FOUND = "EMP-1000";
    public static final String RESOURCE_ALREADY_EXISTS = "EMP-1001";

    /* Validation Error Codes (2000-2999) */
    public static final String VALIDATION_ERROR = "EMP-2000";
    public static final String INVALID_FORMAT = "EMP-2001";

    /* Business Logic Error Codes (3000-3999) */
    public static final String BUSINESS_RULE_VIOLATION = "EMP-3000";
    public static final String OPERATION_NOT_ALLOWED = "EMP-3001";

    /* Security Error Codes (4000-4999) */
    public static final String UNAUTHORIZED = "EMP-4000";
    public static final String FORBIDDEN = "EMP-4001";

    /* Integration Error Codes (5000-5999) */
    public static final String SERVICE_UNAVAILABLE = "EMP-5000";
    public static final String COMMUNICATION_ERROR = "EMP-5001";
    public static final String GATEWAY_TIMEOUT = "EMP-5002";

    /* System Error Codes (9000-9999) */
    public static final String INTERNAL_SERVER_ERROR = "EMP-9000";
    public static final String UNKNOWN_ERROR = "EMP-9999";

}
