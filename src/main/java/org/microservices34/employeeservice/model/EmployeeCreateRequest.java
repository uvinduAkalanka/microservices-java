package org.microservices34.employeeservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EmployeeCreateRequest {
    @Past(message = "Birth date should be in the past")
    @NotNull(message = "birthday cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthDate")
    private LocalDate birthDate;

    @PastOrPresent(message = "hire date should be in the past")
    @NotNull(message = "hireDate cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("hireDate")
    private LocalDate hireDate;

    @NotNull(message = "First name cannot be null")
    @JsonProperty("firstName")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @JsonProperty("lastName")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "gender cannot be null")
    @JsonProperty("gender")
    private Gender gender;
}
