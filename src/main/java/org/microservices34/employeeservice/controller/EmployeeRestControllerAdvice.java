//package org.microservices34.employeeservice.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestControllerAdvice
//public class EmployeeRestControllerAdvice {
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
//        HttpStatus status = HttpStatus.BAD_REQUEST;  // Default status for runtime exceptions
//
//        Map<String, Object> errorResponse = Map.of(
//                "success", false,
//                "errorMessage", ex.getMessage(),
//                "timestamp", LocalDateTime.now(),
//                "status", status.value(),
//                "error", status.getReasonPhrase(),
//                "path", request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(errorResponse, status);
//    }
//
//    @ExceptionHandler(Exception.class)  // Handle generic exceptions
//    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
//        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//        Map<String, Object> errorResponse = Map.of(
//                "success", false,
//                "errorMessage", ex.getMessage(),
//                "timestamp", LocalDateTime.now(),
//                "status", status.value(),
//                "error", status.getReasonPhrase(),
//                "path", request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(errorResponse, status);
//    }
//
//
////    $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
////    @ExceptionHandler(RuntimeException.class)
//////    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
////    public ResponseEntity<Map<String, Object>> handleException(RuntimeException ex) {
////
////        Map<String, Object> errorResponse = Stream.of(
////                Map.entry("success", false),
////                Map.entry("errorMessage", ex.getMessage()),
////                Map.entry("timestamp", LocalDateTime.now())
////        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
////
////        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
////        if (ex instanceof RuntimeException) {
////            status = HttpStatus.BAD_REQUEST;  // Customize for RuntimeException
////        }
////        return new ResponseEntity<>(errorResponse, status);
////    }
////
////    @ExceptionHandler(RuntimeException.class)
////    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
////    public Map<String, Object> handleBadRequestException(RuntimeException ex) {
////        return Stream.of(
////                Map.entry("success", false),
////                Map.entry("errorMessage", ex.getMessage()),
////                Map.entry("timestamp", LocalDateTime.now()),
////                Map.entry("status", HttpStatus.BAD_REQUEST.value()),
////                Map.entry("error", HttpStatus.BAD_REQUEST.getReasonPhrase()),
////                Map.entry("path", "/api/employees")
////        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
////
////    }
////@ExceptionHandler(RuntimeException.class)
////    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
////    public Map<String, Object> handleInternalServerErrorException(RuntimeException ex) {
////        return Stream.of(
////                Map.entry("success", false),
////                Map.entry("errorMessage", ex.getMessage()),
////                Map.entry("timestamp", LocalDateTime.now()),
////                Map.entry("status", HttpStatus.INTERNAL_SERVER_ERROR.value()),
////                Map.entry("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
////                Map.entry("path", "/api/employees")
////        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
////
////    }
////    $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//
//
////#################BASIC METHOD#################
////  @ExceptionHandler(RuntimeException.class)
////    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
////    public Map<String, Object> handleException(RuntimeException ex) {
////
//////Using Collectors.toMap() for Flexibility(advanced)
////        return Stream.of(
////                Map.entry("success", false),
////                Map.entry("errorMessage", ex.getMessage())
////        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        //Using Map.of() for Immutable Map (Best for Simplicity)
////        Map<String, Object> error = Map.of(
////                "success", false,
////                "errorMessage", ex.getMessage()
////        );
////ordinary method
////        Map<String, Object> error = new HashMap<>();
////        error.put("success", false);
////        error.put("error message", ex.getMessage());
////    }
//
//
//}
