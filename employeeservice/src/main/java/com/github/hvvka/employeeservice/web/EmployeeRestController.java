package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.domain.Employee;
import com.github.hvvka.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeRestController {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeRestController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse
                .map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable Long id) {
        LOG.info("GET Employee: {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return wrapOrNotFound(employee);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployeeByName(@RequestBody Employee employee) { // fixme: employeeDTO would be nice
        LOG.info("UPDATE Employee: {}", employee);
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.ok().body(result);
    }
}