package com.github.hvvka.employeeservice.service;

import com.github.hvvka.employeeservice.EmployeeserviceApplication;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
//        this.employee = new Employee();
//        this.employee.setId(6L);
//        this.employee.setFirstName("Adam");
//        this.employee.setLastName("Ondra");
//        this.employee.setAge(27);
//        this.employee.setPesel("93020512345");
//        this.employee.setRole(Role.CEO);
//        this.employee.setAddresses(Set.of(
//                new Address().id(4L).addressType(AddressType.SHIPPING)
//        ));
//        this.employee.setSupervisor(null);
    }

    @Test
    void shouldGetExistingEmployeeById() {
        // when
        Optional<EmployeeDTO> adamOndraDTO = employeeService.getEmployeeById(adamOndraId);

        // then
        assertThat(adamOndraDTO.isPresent()).isTrue();
        assertThat(adamOndraDTO.get()).isEqualTo(new EmployeeDTO(adamOndra));
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistingEmployeeId() {
        // given
        long nonExistingId = 2137L;

        // when
        Optional<EmployeeDTO> nonExistingEmployeeDTO = employeeService.getEmployeeById(nonExistingId);

        // then
        assertThat(nonExistingEmployeeDTO.isPresent()).isFalse();
    }

    @Test
    void shouldUpdateEmployee() {
    }
}