package com.github.hvvka.employeeservice.web;

import com.github.hvvka.employeeservice.service.error.PeselAlreadyPresentException;
import com.github.hvvka.employeeservice.service.error.TooManyDirectorsException;
import com.github.hvvka.employeeservice.service.error.TooManyEmployeesForManagerException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TooManyEmployeesForManagerException.class})
    public void handle(TooManyEmployeesForManagerException e) {
        postExceptionToErrorService(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TooManyDirectorsException.class})
    public void handle(TooManyDirectorsException e) {
        postExceptionToErrorService(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PeselAlreadyPresentException.class})
    public void handle(PeselAlreadyPresentException e) {
        postExceptionToErrorService(e);
    }

    private void postExceptionToErrorService(RuntimeException e) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(errorsServiceUrl, e, String.class);
        LOG.info("{}", result);
    }
}
