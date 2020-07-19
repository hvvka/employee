package com.github.hvvka.employeeservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class TooManyEmployeesForManagerException extends RuntimeException {

    public TooManyEmployeesForManagerException() {
        super("Too many employees for the manager!");
    }
}