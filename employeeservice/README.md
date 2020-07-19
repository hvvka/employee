# EmployeeService

[![Build Status](https://travis-ci.com/hvvka/employee.svg?token=AtJu5RATvaNahLGCYye5&branch=master)](https://travis-ci.com/hvvka/employee)

- [x] employee has a first name
- [x] employee has a second name
- [x] employee has an age
- [x] employee has PESEL
- [x] employee can have many addresses
- [x] employee has a role

- [x] fields are validated (firstName, lastName, age, PESEL, address)
- [x] every field can be updated
- [x] the system is stateless

- [x] the system has to show the hierarchy for an employee - who is above and below
- [x] the system has to be containerised (the image on a public repo)
- [wtf] the system has to be 'prod ready' 
- [x] application code has to be on public GIT repository
- [x] the system has to have documentation 
- [x] the system has to be build upon Spring framework

Business exceptions are thrown when:
- [] the user with the same PESEL already exists
- [x] the manager manages \>5 employees 
- [x] \>5 directors exist

--- 

Nice to have:
- [x] DB other than H2
- [] Spring API gateway
- [x] Swagger
- [] LiquiBase
- [] cache strategy
- [x] Travis CI
- [x] tests
- [] have H2 in-memory with dev profile / H2 file-based for prod

## Documentation

http://localhost:9000/v2/api-docs

http://localhost:9000/swagger-resources/

http://localhost:9000/swagger-ui.html

## Healthcheck

GET localhost:9000/actuator/health

## Docker

Build image
```bash
$ docker build -t hvvka/employeeservice .
```

Pull image
```bash
$ docker pull hvvka/employeeservice
```