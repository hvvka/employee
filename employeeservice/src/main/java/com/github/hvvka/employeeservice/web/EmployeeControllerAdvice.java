package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.service.TooManyEmployeesForManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeControllerAdvice {

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler({TooManyEmployeesForManagerException.class})
    public void handle(TooManyEmployeesForManagerException e) {
    }

}
