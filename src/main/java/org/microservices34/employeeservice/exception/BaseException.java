package org.microservices34.employeeservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;
    private final String moreInfo;

    protected BaseException(String message, String errorCode, HttpStatus status, String moreInfo) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.moreInfo = moreInfo;
    }

    protected BaseException(String message, String errorCode, HttpStatus status) {
        this(message, errorCode, status, null);
    }
}
