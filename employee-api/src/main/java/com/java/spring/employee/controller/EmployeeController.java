package com.java.spring.employee.controller;

import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.java.spring.employee.dto.Employee;
import com.java.spring.employee.service.IEmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	IEmployeeService empServiceRef;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping("/sum/{number1}/{number2}")
	public int sum(@PathVariable int number1,@PathVariable int number2) {
		return number1 + number2;
	}
	
	@RequestMapping("/welcome")
	public List<Employee> welcomeEmployee() {
		String url = "http://localhost:9091/employees";
		Employee objects = restTemplate.getForObject(url, Employee.class);
		return Arrays.asList(objects);
	}
	
	@RequestMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return new ResponseEntity<List<Employee>>((List<Employee>) empServiceRef.getAllEmployees(), HttpStatus.OK);
//		return empServiceRef.getAllEmployees();
	}
	
	@RequestMapping("/employees/{empId}")
	public Employee getEmployeeById(@PathVariable int empId) {
		return empServiceRef.getEmployeeById(empId);
	}
	
	@RequestMapping("/employees/department")
	public List<Employee> getEmployeeByFirstNameAndDeptName(@RequestBody Employee employee) {
		return empServiceRef.getEmployeeByFirstNameAndDeptName(employee.getDeptName(),String.valueOf(employee.getEmpId()));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/employees")
	public void addEmployee(@RequestBody Employee employee) {
		empServiceRef.addEmployee(employee);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/employees/{empId}")
	public void updateEmployee(@RequestBody Employee employee,@PathVariable int empId) {
		empServiceRef.updateEmployee(employee,empId);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/employees/{empId}")
	public void deleteEmployee(@PathVariable int empId) {
		empServiceRef.deleteEmployee(empId);
	}
}
