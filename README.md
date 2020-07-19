# employee

Compose
```bash
$ docker-compose up --scale employeeservice=2 -d
```

Only DB (for dev)
```bash
$ docker-compose -f mysql.yml up
```

Services

- employeeservice_1 (port 9000)
- employeeservice_2 (port 9001)
- errorservice (port 8080)
- mysql (port 3306)
