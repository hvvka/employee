package com.github.hvvka.employeeservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hvvka.employeeservice.domain.Address;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
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
        if (employee.getSupervisor().isPresent()) {
            this.supervisorId = employee.getSupervisor().get().getId();
        }
        this.setSubordinateIds(employee.getSubordinates().stream()
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
    public Optional<Long> getOptionalSupervisorId() {
        return supervisorId == null ? Optional.empty() : Optional.of(supervisorId);
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Set<Long> getSubordinateIds() {
        return subordinateIds;
    }

    public void setSubordinateIds(Set<Long> subordinateIds) {
        this.subordinateIds = subordinateIds;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return id.equals(that.id) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                Objects.equals(age, that.age) &&
                Objects.equals(pesel, that.pesel) &&
                role == that.role &&
                Objects.equals(addresses, that.addresses) &&
                Objects.equals(supervisorId, that.supervisorId) &&
                Objects.equals(subordinateIds, that.subordinateIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, pesel, role, addresses, supervisorId, subordinateIds);
    }
}
