package org.microservices34.employeeservice.exception;

import org.springframework.http.HttpStatus;

public class IntegrationException extends BaseException {
    public IntegrationException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }

    public IntegrationException(String message) {
        super(message, ErrorCodes.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
