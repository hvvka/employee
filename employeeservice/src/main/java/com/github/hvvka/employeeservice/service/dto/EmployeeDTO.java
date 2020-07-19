package com.github.hvvka.employeeservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hvvka.employeeservice.domain.Address;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDTO {

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Min(0)
    @Max(125)
    private Integer age;

    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    private Role role;

    private Set<Long> addresses;

    private Long employeeAboveId;

    private Set<Long> employeeBelowIds = new HashSet<>();

    public EmployeeDTO() {
        // for Jackson
    }

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
        if (employee.getEmployeeAbove().isPresent()) {
            this.employeeAboveId = employee.getEmployeeAbove().get().getId();
        }
        this.setEmployeeBelowIds(employee.getEmployeesBelow().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Long> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Long> addresses) {
        this.addresses = addresses;
    }

    @JsonIgnore
    public Optional<Long> getOptionalEmployeeAboveId() {
        return employeeAboveId == null ? Optional.empty() : Optional.of(employeeAboveId);
    }

    public Long getEmployeeAboveId() {
        return employeeAboveId;
    }

    public void setEmployeeAboveId(Long employeeAboveId) {
        this.employeeAboveId = employeeAboveId;
    }

    public Set<Long> getEmployeeBelowIds() {
        return employeeBelowIds;
    }

    public void setEmployeeBelowIds(Set<Long> employeeBelowIds) {
        this.employeeBelowIds = employeeBelowIds;
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
                ", employeeAboveId=" + employeeAboveId +
                ", employeeBelowIds=" + employeeBelowIds +
                '}';
    }
}
