package org.microservices34.employeeservice.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String serviceName;
    private String correlationId;
    private String requestId;
    private int status;
    private String code;
    private String message;
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp;

    private Map<String, String> errors;
    private List<ValidationError> validationErrors;
    private String moreInfo;

    @Data
    @Builder
    public static class ValidationError {
        private String field;
        private String message;
        private String rejectedValue;
    }

}
