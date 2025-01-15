package com.taifex.openapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taifex.openapi.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
