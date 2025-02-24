package org.microservices34.employeeservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservices34.employeeservice.entity.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
    private int employeeNumber;
    @Past(message = "Birth date should be in the past")
    @NotNull(message = "birthday cannot be null")
    private LocalDate birthDate;

    @PastOrPresent(message = "hire date should be in the past")
    @NotNull(message = "First name cannot be null")
    private LocalDate hireDate;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    private String lastName;

    @NotNull(message = "gender cannot be null")
    private Gender gender;
}
