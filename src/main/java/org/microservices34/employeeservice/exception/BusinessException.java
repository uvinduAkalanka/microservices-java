package org.microservices34.employeeservice.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException{

    public BusinessException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }

    public BusinessException(String message, String errorCode, HttpStatus status, String moreInfo) {
        super(message, errorCode, status, moreInfo);
    }
}
