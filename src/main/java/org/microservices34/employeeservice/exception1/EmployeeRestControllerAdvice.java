//package org.microservices34.employeeservice.exception;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Builder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//
//@Slf4j
//@RestControllerAdvice
//@Builder
//public class EmployeeRestControllerAdvice {
//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
//        log.error("API Exception: {}", ex.getMessage());
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .success(false)
//                .errorMessage(ex.getMessage())
//                .timestamp(LocalDateTime.now())
//                .status(ex.getStatus().value())
//                .error(ex.getStatus().getReasonPhrase())
//                .path(request.getRequestURI())
//                .build();
//
//        return new ResponseEntity<>(errorResponse, ex.getStatus());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
//        log.error("Unhandled Exception: {}", ex.getMessage());
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .success(false)
//                .errorMessage("An unexpected error occurred.")
//                .timestamp(LocalDateTime.now())
//                .status(500)
//                .error("Internal Server Error")
//                .path(request.getRequestURI())
//                .build();
//
//        return ResponseEntity.internalServerError().body(errorResponse);
//    }
//}
