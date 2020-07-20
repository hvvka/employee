package com.github.hvvka.employeeservice.service;

import com.github.hvvka.employeeservice.EmployeeserviceApplication;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import com.github.hvvka.employeeservice.service.error.PeselAlreadyPresentException;
import com.github.hvvka.employeeservice.service.error.TooManyDirectorsException;
import com.github.hvvka.employeeservice.service.error.TooManyEmployeesForManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = EmployeeserviceApplication.class)
@Transactional
class EmployeeServiceTest {

    private final long adamOndraId = 6L;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    private Employee adamOndra;

    @BeforeEach
    void setUp() {
        this.adamOndra = employeeRepository.getOne(adamOndraId);
    }

    @Test
    void shouldGetExistingEmployeeById() {
        // when
        Optional<EmployeeDTO> adamOndraDTO = employeeService.getEmployeeById(adamOndraId);

        // then
        assertThat(adamOndraDTO).isPresent();
        assertThat(adamOndraDTO.get()).isEqualTo(new EmployeeDTO(adamOndra));
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingEmployeeId() {
        // given
        long nonExistingId = 2137L;

        // when
        Optional<EmployeeDTO> nonExistingEmployeeDTO = employeeService.getEmployeeById(nonExistingId);

        // then
        assertThat(nonExistingEmployeeDTO).isNotPresent();
    }

    @Test
    void shouldUpdateEmployeeLastName() {
        // given
        EmployeeDTO adamOndraDTO = new EmployeeDTO(adamOndra);
        adamOndraDTO.setLastName("Flondra");

        // when
        Optional<EmployeeDTO> adamFlondraDTO = employeeService.updateEmployee(adamOndraDTO);

        // then
        assertThat(adamFlondraDTO).isPresent();
        assertThat(adamFlondraDTO.get()).isEqualTo(adamOndraDTO);
    }

    @Test
    void shouldThrowTooManyEmployeesForManagerExceptionOnUpdateEmployeeSupervisor() {
        // given
        EmployeeDTO adamOndraDTO = new EmployeeDTO(adamOndra);
        adamOndraDTO.setSupervisorId(3L);

        // expect
        assertThatExceptionOfType(TooManyEmployeesForManagerException.class)
                .isThrownBy(() -> employeeService.updateEmployee(adamOndraDTO))
                .withMessage("Too many employees for the manager! Max allowed: 5");
    }

    @Test
    void shouldThrowTooManyEmployeesForManagerExceptionOnUpdateEmployeeRole() {
        // given
        EmployeeDTO testoDTO = new EmployeeDTO(employeeRepository.getOne(1L));
        testoDTO.setRole(Role.MANAGER);

        // expect
        assertThatExceptionOfType(TooManyEmployeesForManagerException.class)
                .isThrownBy(() -> employeeService.updateEmployee(testoDTO))
                .withMessage("Too many employees for the manager! Max allowed: 5");
    }

    @Test
    void shouldThrowTooManyDirectorsExceptionOnUpdateEmployee() {
        // given
        EmployeeDTO adamOndraDTO = new EmployeeDTO(adamOndra);
        adamOndraDTO.setRole(Role.DIRECTOR);

        // expect
        assertThatExceptionOfType(TooManyDirectorsException.class)
                .isThrownBy(() -> employeeService.updateEmployee(adamOndraDTO))
                .withMessage("Too many directors! Max allowed: 5");
    }

    @Test
    void shouldThrowPeselAlreadyPresentExceptionOnUpdateEmployee() {
        // given
        EmployeeDTO adamOndraDTO = new EmployeeDTO(adamOndra);
        adamOndraDTO.setPesel("79072512345");

        // expect
        assertThatExceptionOfType(PeselAlreadyPresentException.class)
                .isThrownBy(() -> employeeService.updateEmployee(adamOndraDTO))
                .withMessage("There is already an employee with the same PESEL!");
    }
}