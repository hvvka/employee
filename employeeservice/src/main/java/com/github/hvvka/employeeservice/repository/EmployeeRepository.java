package com.github.hvvka.employeeservice.repository;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Set<Employee> findAllByRoleIs(Role role);

    boolean existsByPesel(String pesel);
}
