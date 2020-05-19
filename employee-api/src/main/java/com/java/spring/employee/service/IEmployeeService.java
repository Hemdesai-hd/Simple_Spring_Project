package com.java.spring.employee.service;

import java.util.List;

import com.java.spring.employee.dto.Employee;

public interface IEmployeeService{

	List<Employee> getAllEmployees();
	Employee getEmployeeById(int empId);
	void addEmployee(Employee employee);
	void updateEmployee(Employee employee, int empId);
	void deleteEmployee(int empId);

}
