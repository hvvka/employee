package com.github.hvvka.employeeservice.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyEmployeesForManagerException extends RuntimeException {

    public TooManyEmployeesForManagerException(int maxManagerSubordinates) {
        super(String.format("Too many employees for the manager! Max allowed: %d", maxManagerSubordinates));
    }
}