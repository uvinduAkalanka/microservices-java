package org.microservices34.employeeservice.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ServiceProperties {
    private String name = "employee-service";
    private boolean includeStackTrace = false;
    private String supportEmail = "support@example.com";
    private String docsUrl = "https://docs.example.com/errors";
}