# EmployeeService

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
- [] the system has to be containerised (the image on public repo)
- [] the system has to be 'prod ready'
- [x] application code has to be on public GIT repository
- [] the system has to have documentation 
- [x] the system has to be build upon Spring framework

Bussiness exceptions are thrown when:
- [] the user with the same PESEL already exists
- [] the manager manages >5 employees
- [] >5 directors exist

--- 

Nice to have:
- [] DB other than H2
- [] Spring API gateway
- [] Swagger
- [] LiquiBase
- [] cache strategy
- [] Travis CI
- [] tests
