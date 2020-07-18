package com.github.hvvka.employeeservice.repository;

import com.github.hvvka.employeeservice.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstName(String firstName);
}
