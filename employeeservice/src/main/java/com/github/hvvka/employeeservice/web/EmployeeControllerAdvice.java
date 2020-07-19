package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.service.TooManyEmployeesForManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

@ControllerAdvice
public class EmployeeControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeControllerAdvice.class);

    @Value("${errorservice.url}")
    private String errorsServiceUrl;

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler({TooManyEmployeesForManagerException.class})
    public void handle(TooManyEmployeesForManagerException e) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(errorsServiceUrl, e, String.class);
        LOG.info("{}", result);
    }
}
