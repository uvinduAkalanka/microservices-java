package org.microservices34.employeeservice.service.impl;

import org.microservices34.employeeservice.entity.Employee;
import org.microservices34.employeeservice.exception.ResourceNotFoundException;
import org.microservices34.employeeservice.model.EmployeeCreateRequest;
import org.microservices34.employeeservice.model.EmployeeResponse;
import org.microservices34.employeeservice.repository.EmployeeRepository;
import org.microservices34.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest employeeRequest) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        var savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponse.class);
    }

    @Override
    public List<EmployeeResponse> listAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
//                .flatMap(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Employee ", "id", id));
    }

    @Override
    public void deleteEmployeeById(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
        employeeRepository.deleteById(id);
    }
}
