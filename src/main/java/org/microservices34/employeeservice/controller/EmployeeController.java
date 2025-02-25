package org.microservices34.employeeservice.controller;

import jakarta.validation.Valid;
import org.microservices34.employeeservice.model.EmployeeCreateRequest;
import org.microservices34.employeeservice.model.EmployeeResponse;
import org.microservices34.employeeservice.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeResponse createEmployee(@RequestBody @Valid EmployeeCreateRequest employeeRequest) {
        EmployeeResponse employee = employeeService.createEmployee(employeeRequest);
        return employee;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeResponse> getAllEmployees() {
        System.out.println("hello");
        return employeeService.listAllEmployees();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeResponse getEmployeeById(@PathVariable int id) {
        employeeService.getEmployeeById(id);
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteEmployeeById(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);
    }
}

