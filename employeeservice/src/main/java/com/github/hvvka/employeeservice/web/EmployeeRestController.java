package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.service.EmployeeService;
import com.github.hvvka.employeeservice.service.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class);

    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse
                .map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        LOG.info("GET Employee: {}", id);
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return wrapOrNotFound(employeeDTO);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        LOG.info("UPDATE Employee: {}", employeeDTO);
        employeeDTO.setId(id);
        Optional<EmployeeDTO> updatedEmployee = employeeService.updateEmployee(employeeDTO);
        return wrapOrNotFound(updatedEmployee);
    }
}