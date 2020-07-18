package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeRestController {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeRestController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employee/{firstName}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String firstName) {
        LOG.info("GET Employee: {}", firstName);
        Employee employee = employeeRepository.findByFirstName(firstName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}