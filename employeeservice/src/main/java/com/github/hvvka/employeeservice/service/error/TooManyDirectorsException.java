package com.github.hvvka.employeeservice.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyDirectorsException extends RuntimeException {

    public TooManyDirectorsException(int maxDirectors) {
        super(String.format("Too many directors! Max allowed: %d", maxDirectors));
    }
}
