package org.microservices34.employeeservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class RequestLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String correlationId = httpRequest.getHeader("X-Correlation-ID");
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        String requestId = UUID.randomUUID().toString();

        // Add IDs to MDC for logging
        MDC.put("correlationId", correlationId);
        MDC.put("requestId", requestId);

        // Add IDs to response headers
        httpResponse.setHeader("X-Correlation-ID", correlationId);
        httpResponse.setHeader("X-Request-ID", requestId);

        long startTime = System.currentTimeMillis();
        try {
            log.info("Incoming request [method={}, uri={}, correlationId={}]", httpRequest.getMethod(), httpRequest.getRequestURI(), correlationId);
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("Request completed [method={}, uri={}, status={}, duration={}ms, correlationId={}]", httpRequest.getMethod(), httpRequest.getRequestURI(), httpResponse.getStatus(), duration, correlationId);
            MDC.clear();
        }
    }

}
