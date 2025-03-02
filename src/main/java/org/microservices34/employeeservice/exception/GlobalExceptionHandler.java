package org.microservices34.employeeservice.exception;

//import com.fasterxml.jackson.databind.JsonMappingException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolationException;
//import lombok.RequiredArgsConstructor;
//import org.microservices34.employeeservice.exception.model.ErrorResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.microservices34.employeeservice.exception.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ServiceProperties serviceProperties;

    /**
     * Handle base exception which all custom exceptions inherit from
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        logError(ex);

        ErrorResponse errorResponse = buildErrorResponse(
                ex.getStatus().value(),
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                ex.getMoreInfo()
        );

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * Handle resource not found exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        logError(ex);

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle validation exceptions from @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        logError(ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        Map<String, String> fieldErrors = new HashMap<>();
        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(this::mapToValidationError)
                .collect(Collectors.toList());

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCodes.VALIDATION_ERROR,
                "Validation failed",
                path
        );
        errorResponse.setErrors(fieldErrors);
        errorResponse.setValidationErrors(validationErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violations from @Validated
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        logError(ex);

        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> extractFieldName(violation),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                ));

        List<ErrorResponse.ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(this::mapToValidationError)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCodes.VALIDATION_ERROR,
                "Validation failed",
                request.getRequestURI()
        );
        errorResponse.setErrors(errors);
        errorResponse.setValidationErrors(validationErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle JSON parsing exceptions
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponse> handleJsonMappingException(
            JsonMappingException ex, HttpServletRequest request) {

        logError(ex);

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCodes.INVALID_FORMAT,
                "Invalid JSON format: " + ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle all other unhandled exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception occurred", ex);

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCodes.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later or contact support.",
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Build a standard error response
     */
    private ErrorResponse buildErrorResponse(
            int status, String code, String message, String path) {
        return buildErrorResponse(status, code, message, path, null);
    }

    /**
     * Build a standard error response with more info
     */
    private ErrorResponse buildErrorResponse(
            int status, String code, String message, String path, String moreInfo) {

        return ErrorResponse.builder()
                .serviceName(serviceProperties.getName())
                .status(status)
                .code(code)
                .message(message)
                .path(path)
                .correlationId(MDC.get("correlationId"))
                .requestId(MDC.get("requestId"))
                .timestamp(LocalDateTime.now())
                .moreInfo(Objects.isNull(moreInfo) ?
                        serviceProperties.getDocsUrl() + "/errors/" + code : moreInfo)
                .build();
    }

    /**
     * Map field errors to validation errors
     */
    private ErrorResponse.ValidationError mapToValidationError(FieldError fieldError) {
        return ErrorResponse.ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .rejectedValue(fieldError.getRejectedValue() != null ?
                        fieldError.getRejectedValue().toString() : null)
                .build();
    }

    /**
     * Map constraint violations to validation errors
     */
    private ErrorResponse.ValidationError mapToValidationError(ConstraintViolation<?> violation) {
        return ErrorResponse.ValidationError.builder()
                .field(extractFieldName(violation))
                .message(violation.getMessage())
                .rejectedValue(violation.getInvalidValue() != null ?
                        violation.getInvalidValue().toString() : null)
                .build();
    }

    /**
     * Extract field name from constraint violation
     */
    private String extractFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        int lastDotIndex = propertyPath.lastIndexOf('.');
        return lastDotIndex > 0 ? propertyPath.substring(lastDotIndex + 1) : propertyPath;
    }

    /**
     * Log error with correlation ID and request details
     */
    private void logError(Exception ex) {
        String correlationId = MDC.get("correlationId");
        String requestId = MDC.get("requestId");

        if (ex instanceof BaseException) {
            log.error("Error processing request [correlationId={}, requestId={}, errorCode={}]: {}",
                    correlationId, requestId, ((BaseException) ex).getErrorCode(), ex.getMessage());
        } else {
            log.error("Error processing request [correlationId={}, requestId={}]: {}",
                    correlationId, requestId, ex.getMessage(), ex);
        }
    }
}
