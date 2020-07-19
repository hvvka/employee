package com.github.hvvka.employeeservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TooManyDirectorsException extends RuntimeException {

    public TooManyDirectorsException() {
        super("Too many directors!");
    }
}
