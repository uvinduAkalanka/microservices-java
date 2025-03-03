package org.microservices34.employeeservice.service;


import org.microservices34.employeeservice.model.EmployeeCreateRequest;
import org.microservices34.employeeservice.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService  {
    EmployeeResponse createEmployee(EmployeeCreateRequest employeeRequest);

    List<EmployeeResponse> listAllEmployees();

    EmployeeResponse getEmployeeById(int id);

    void deleteEmployeeById(int id);
}
