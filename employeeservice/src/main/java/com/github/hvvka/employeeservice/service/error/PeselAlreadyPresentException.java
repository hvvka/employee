package com.github.hvvka.employeeservice.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PeselAlreadyPresentException extends RuntimeException {

    public PeselAlreadyPresentException() {
        super("There is already an employee with the same PESEL!");
    }
}
