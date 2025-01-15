package com.taifex.openapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taifex.openapi.entity.Employee;
import com.taifex.openapi.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	// Create
	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	// Read all
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	// Read by ID
	public Optional<Employee> getEmployeeById(Long id) {
		return repository.findById(id);
	}

	// Update
	public Employee updateEmployee(Long id, Employee employeeDetails) {
		Employee employee = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
		employee.setName(employeeDetails.getName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setDepartment(employeeDetails.getDepartment());
		return repository.save(employee);
	}

	// Delete
	public void deleteEmployee(Long id) {
		repository.deleteById(id);
	}
}
