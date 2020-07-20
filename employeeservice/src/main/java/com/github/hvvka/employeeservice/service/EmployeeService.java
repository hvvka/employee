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
                    checkBusinessLogic(employeeDTO, employee);
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

    private void checkBusinessLogic(EmployeeDTO employeeDTO, Employee employee) {
        if (hasManagerTooManySubordinates(employee, employeeDTO.getRole())
                || hasNewManagerOfSubordinateDTOTooManySubordinates(employee, employeeDTO)) {
            throw new TooManyEmployeesForManagerException(MAX_MANAGER_SUBORDINATES);
        } else if (areThereTooManyDirectors(employeeDTO)) {
            throw new TooManyDirectorsException(MAX_DIRECTORS);
        } else if (!employee.getPesel().equals(employeeDTO.getPesel())
                && employeeRepository.existsByPesel(employeeDTO.getPesel())) {
            throw new PeselAlreadyPresentException();
        }
    }

    private boolean hasManagerTooManySubordinates(Employee employee, Role newRole) {
        return (employee.getRole() != Role.MANAGER && newRole == Role.MANAGER)
                && employee.getSubordinates().size() > MAX_MANAGER_SUBORDINATES;
    }

    private boolean hasNewManagerOfSubordinateDTOTooManySubordinates(Employee employee, EmployeeDTO employeeDTO) {
        Optional<Employee> optionalOldSupervisor = employee.getSupervisor();
        Optional<Long> optionalNewSupervisorId = employeeDTO.getOptionalSupervisorId();
        if (optionalNewSupervisorId.isPresent()
                && (optionalOldSupervisor.isEmpty()
                || !optionalNewSupervisorId.get().equals(optionalOldSupervisor.get().getId()))) {
            Optional<Employee> optionalSupervisor = employeeRepository.findById(optionalNewSupervisorId.get());
            return optionalSupervisor
                    .filter(s -> s.getRole() == Role.MANAGER
                            && s.getSubordinates().size() >= MAX_MANAGER_SUBORDINATES)
                    .isPresent();
        }
        return false;
    }

    private boolean areThereTooManyDirectors(EmployeeDTO employeeDTO) {
        if (employeeDTO.getRole() != Role.DIRECTOR) {
            return false;
        }
        Set<Long> directorIds = employeeRepository.findAllByRoleIs(Role.DIRECTOR).stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
        return !directorIds.contains(employeeDTO.getId())
                && directorIds.size() >= MAX_DIRECTORS;
    }
}
