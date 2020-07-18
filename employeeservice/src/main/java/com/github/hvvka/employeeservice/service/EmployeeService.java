package com.github.hvvka.employeeservice.service;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.repository.AddressRepository;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
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
                .map(EmployeeDTO::new)
                .map(e -> fillEmployeeAboveInfo(id, e));
    }

    public Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
        return Optional.of(employeeRepository.findById(employeeDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
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
                .map(EmployeeDTO::new)
                .map(e -> fillEmployeeAboveInfo(employeeDTO.getId(), e));
    }

    private EmployeeDTO fillEmployeeAboveInfo(Long id, EmployeeDTO e) {
        e.setEmployeeBelowIds(
                employeeRepository.findAllByEmployeeAbove_Id(id).stream()
                        .map(Employee::getId)
                        .collect(Collectors.toSet()));
        return e;
    }
}
