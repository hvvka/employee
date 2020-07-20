package com.github.hvvka.employeeservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hvvka.employeeservice.EmployeeserviceApplication;
import com.github.hvvka.employeeservice.domain.Address;
import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.domain.enumeration.AddressType;
import com.github.hvvka.employeeservice.domain.enumeration.Role;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import com.github.hvvka.employeeservice.service.EmployeeService;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = EmployeeserviceApplication.class)
class EmployeeRestControllerIT {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mvc;

    private Employee employee;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        EmployeeRestController employeeRestController = new EmployeeRestController(employeeService);
        this.mvc = MockMvcBuilders.standaloneSetup(employeeRestController).build();
        this.employee = new Employee();
        this.employee.setId(1L);
        this.employee.setFirstName("Ryan");
        this.employee.setLastName("Gosling");
        this.employee.setAge(39);
        this.employee.setPesel("80111212345");
        this.employee.setRole(Role.DIRECTOR);
        this.employee.setAddresses(Set.of(
                new Address(1L, AddressType.HOME)
        ));
        this.employee.setSupervisor(null);
    }

    @Test
    @Transactional
    void shouldGetEmployee() throws Exception {
        // setup
        employeeRepository.saveAndFlush(employee);

        // expect
        mvc.perform(get("/api/employee/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ryan"))
                .andExpect(jsonPath("$.lastName").value("Gosling"))
                .andExpect(jsonPath("$.age").value(39))
                .andExpect(jsonPath("$.pesel").value("80111212345"))
                .andExpect(jsonPath("$.role").value("DIRECTOR"))
                .andExpect(jsonPath("$.addresses.[0]").value(1))
                .andExpect(jsonPath("$.supervisorId").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.subordinateIds").isArray());
    }

    @Test
    @Transactional
    void shouldReturnOkWhenUpdateEmployee() throws Exception {
        // given
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setFirstName("XD");
        employeeDTO.setLastName("!!");

        // when
        mvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());

        // then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employees.get(0);
        assertThat(testEmployee.getFirstName()).isEqualTo("XD");
        assertThat(testEmployee.getLastName()).isEqualTo("!!");
    }

    @Test
    @Transactional
    public void shouldReturnBadRequestWhenExceedsMaxSubordinatesForManager() throws Exception {
        // given
        Employee employee = employeeRepository.getOne(6L);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setSupervisorId(3L);

        // when
        mvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());

        // then
        List<Employee> employees = employeeRepository.findAll();
        Employee testEmployee = employees.get(5);
        assertThat(testEmployee.getRole()).isEqualTo(Role.CEO);
    }

    @Test
    @Transactional
    public void shouldReturnBadRequestWhenExceedsMaxDirectors() throws Exception {
        // given
        Employee employee = employeeRepository.getOne(6L);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setRole(Role.DIRECTOR);

        // when
        mvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());

        // then
        List<Employee> employees = employeeRepository.findAll();
        Employee testEmployee = employees.get(5);
        assertThat(testEmployee.getRole()).isEqualTo(Role.CEO);
    }

    @Test
    @Transactional
    public void shouldReturnBadRequestWhenPeselAlreadyExists() throws Exception {
        // given
        Employee employee = employeeRepository.getOne(6L);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setPesel("20051812345");

        // when
        mvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());

        // then
        List<Employee> employees = employeeRepository.findAll();
        Employee testEmployee = employees.get(5);
        assertThat(testEmployee.getPesel()).isEqualTo("93020512345");
    }
}