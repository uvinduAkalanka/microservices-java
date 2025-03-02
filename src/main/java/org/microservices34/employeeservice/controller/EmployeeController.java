package org.microservices34.employeeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.microservices34.employeeservice.model.EmployeeCreateRequest;
import org.microservices34.employeeservice.model.EmployeeResponse;
import org.microservices34.employeeservice.service.EmployeeService;
import org.microservices34.employeeservice.service.impl.ResourceFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final ResourceFilterService resourceFilterService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeCreateRequest employeeRequest) {
        logger.info("REST request to create Employee: {}", employeeRequest);
        EmployeeResponse response = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(required = false) String fields) {
        logger.info("REST request to get all Employees with fields filter: {}", fields);
        List<EmployeeResponse> employees = employeeService.listAllEmployees();
        List<EmployeeResponse> filteredEmployees = resourceFilterService.filterFields(employees, fields, EmployeeResponse.class);
        return ResponseEntity.ok(filteredEmployees);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        logger.info("REST request to get Employee : {}", id);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).body(employeeService.getEmployeeById(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) {
        logger.info("REST request to delete Employee : {}", id);
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}

