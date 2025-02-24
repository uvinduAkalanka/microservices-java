package org.microservices34.employeeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeNumber;

    @Column(name = "birth_date")
    @Past(message = "Birth date should be in the past")
    @NotNull
    private LocalDate birthDate;

    @Column(name = "hire_date")
    @PastOrPresent(message = "hire date should be in the past")
    @NotNull
    private LocalDate hireDate;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

}
