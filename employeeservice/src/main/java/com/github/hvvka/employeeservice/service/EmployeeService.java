package com.github.hvvka.employeeservice.service;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import com.github.hvvka.employeeservice.repository.AddressRepository;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import com.github.hvvka.employeeservice.service.error.PeselAlreadyPresentException;
import com.github.hvvka.employeeservice.service.error.TooManyDirectorsException;
import com.github.hvvka.employeeservice.service.error.TooManyEmployeesForManagerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    public static final int MAX_DIRECTORS = 5;

    public static final int MAX_MANAGER_SUBORDINATES = 5;

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
                    if (hasEmployeeUpdatedToManagerTooManySubordinates(employee, employeeDTO.getRole())
                            || hasManagerOfUpdatedSubordinateTooManySubordinates(employeeDTO)) {
                        throw new TooManyEmployeesForManagerException(MAX_MANAGER_SUBORDINATES);
                    } else if (areThereTooManyDirectors(employeeDTO)) {
                        throw new TooManyDirectorsException(MAX_DIRECTORS);
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
                    if (employeeDTO.getOptionalSupervisorId().isPresent()) {
                        e.setSupervisor(employeeRepository.findById(employeeDTO.getOptionalSupervisorId().get()).orElse(null));
                    }
                    return e;
                })
                .map(EmployeeDTO::new);
    }

    private boolean hasManagerOfUpdatedSubordinateTooManySubordinates(EmployeeDTO employeeDTO) {
        if (employeeDTO.getOptionalSupervisorId().isPresent()) {
            return employeeRepository.findById(employeeDTO.getOptionalSupervisorId().get())
                    .filter(e -> hasEmployeeUpdatedToManagerTooManySubordinates(e, e.getRole()))
                    .isPresent();
        }
        return false;
    }

    private boolean hasEmployeeUpdatedToManagerTooManySubordinates(Employee employee, Role role) {
        return role == Role.MANAGER && employee.getSubordinates().size() >= MAX_MANAGER_SUBORDINATES;
    }

    private boolean areThereTooManyDirectors(EmployeeDTO employeeDTO) {
        Set<Long> directorIds = employeeRepository.findAllByRoleIs(Role.DIRECTOR).stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
        return employeeDTO.getRole() == Role.DIRECTOR
                && !directorIds.contains(employeeDTO.getId())
                && directorIds.size() >= MAX_DIRECTORS;
    }
}
