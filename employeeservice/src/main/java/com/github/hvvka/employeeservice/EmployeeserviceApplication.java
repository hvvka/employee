package com.github.hvvka.employeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.github.hvvka.employeeservice")
@EnableJpaRepositories("com.github.hvvka.employeeservice")
@SpringBootApplication(scanBasePackages = "com.github.hvvka.employeeservice")
public class EmployeeserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeserviceApplication.class, args);
    }
}
