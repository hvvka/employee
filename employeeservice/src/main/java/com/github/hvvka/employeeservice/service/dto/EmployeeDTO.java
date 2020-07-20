package com.github.hvvka.employeeservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hvvka.employeeservice.domain.Address;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDTO {

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Min(0)
    @Max(125)
    private Integer age;

    @Pattern(regexp = "[0-9]{11}")
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    private Role role;

    private Set<Long> addresses;

    private Long supervisorId;

    private Set<Long> subordinateIds = new HashSet<>();

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.age = employee.getAge();
        this.pesel = employee.getPesel();
        this.role = employee.getRole();
        this.addresses = employee.getAddresses().stream()
                .map(Address::getId)
                .collect(Collectors.toSet());
        if (employee.getSupervisor().isPresent()) {
            this.supervisorId = employee.getSupervisor().get().getId();
        }
        this.setSubordinateIds(employee.getSubordinates().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet()));
    }

    @JsonIgnore
    public Optional<Long> getOptionalSupervisorId() {
        return supervisorId == null ? Optional.empty() : Optional.of(supervisorId);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", pesel='" + pesel + '\'' +
                ", role=" + role +
                ", addresses=" + addresses +
                ", supervisorId=" + supervisorId +
                ", subordinateIds=" + subordinateIds +
                '}';
    }
}
