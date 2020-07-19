package com.github.hvvka.employeeservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PeselAlreadyPresentException extends RuntimeException {

    public PeselAlreadyPresentException() {
        super("There is already an employee with the same PESEL!");
    }
}
