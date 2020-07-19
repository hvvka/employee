package com.github.hvvka.employeeservice.service;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import com.github.hvvka.employeeservice.repository.AddressRepository;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final AddressRepository addressRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(EmployeeDTO::new);
    }

    public Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
        return Optional.of(employeeRepository.findById(employeeDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(employee -> {
                    // todo: move the rule to domain or dto / use hibernate validator
                    if (hasEmployeeUpdatedToManagerTooManyEmployees(employee, employeeDTO.getRole(), 5)
                            || hasManagerOfUpdatedEmployeeTooManyEmployees(employeeDTO)) {
                        throw new TooManyEmployeesForManagerException();
                    } else if (isTooManyDirectors(employeeDTO)) {
                        throw new TooManyDirectorsException();
                    } else if (employeeRepository.existsByPesel(employeeDTO.getPesel())) {
                        throw new PeselAlreadyPresentException();
                    }
                    return employee;
                })
                .map(e -> {
                    e.setFirstName(employeeDTO.getFirstName());
                    e.setLastName(employeeDTO.getLastName());
                    e.setAge(employeeDTO.getAge());
                    e.setRole(employeeDTO.getRole());
                    e.setPesel(employeeDTO.getPesel());
                    e.setAddresses(new HashSet<>(addressRepository.findAllById(employeeDTO.getAddresses())));
                    if (employeeDTO.getOptionalEmployeeAboveId().isPresent()) {
                        e.setEmployeeAbove(employeeRepository.findById(employeeDTO.getOptionalEmployeeAboveId().get()).orElse(null));
                    }
                    return e;
                })
                .map(EmployeeDTO::new);
    }

    private boolean hasManagerOfUpdatedEmployeeTooManyEmployees(EmployeeDTO employeeDTO) {
        return employeeDTO.getRole() == Role.EMPLOYEE
                && employeeRepository.findById(employeeDTO.getEmployeeAboveId())
                .filter(e -> hasEmployeeUpdatedToManagerTooManyEmployees(e, e.getRole(), 4))
                .isPresent();
    }

    private boolean hasEmployeeUpdatedToManagerTooManyEmployees(Employee employee, Role role, int maxEmployees) {
        return role == Role.MANAGER && employee.getEmployeesBelow().size() > maxEmployees;
    }

    private boolean isTooManyDirectors(EmployeeDTO employeeDTO) {
        Set<Long> directorIds = employeeRepository.findAllByRoleIs(Role.DIRECTOR).stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
        return employeeDTO.getRole() == Role.DIRECTOR
                && !directorIds.contains(employeeDTO.getId())
                && directorIds.size() > 4;
    }
}
