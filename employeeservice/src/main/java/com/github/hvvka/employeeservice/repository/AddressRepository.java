package com.github.hvvka.employeeservice.repository;

import com.github.hvvka.employeeservice.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
