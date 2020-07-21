# employee

[![Build Status](https://travis-ci.com/hvvka/employee.svg?token=AtJu5RATvaNahLGCYye5&branch=master)](https://travis-ci.com/hvvka/employee)

## Get the system running

Compose
```bash
$ docker-compose up --scale employeeservice=2 -d
```

Just DB (for dev)
```bash
$ docker-compose -f mysql.yml up
```

Services

- employeeservice_1 (port 9000)
- employeeservice_2 (port 9001)
- errorservice (port 8080)
- mysql (port 3306)

## Postman collection to play with

[employeeservice collection](https://documenter.getpostman.com/view/4256589/T1DmEJsu)

Same requests can be done both for 9000 and 9001.
