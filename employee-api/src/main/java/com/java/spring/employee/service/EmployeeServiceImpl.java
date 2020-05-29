package com.java.spring.employee.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.employee.dao.IEmployeeDAO;
import com.java.spring.employee.dto.Employee;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

	List<Employee> empList = new ArrayList<>(Arrays.asList(
			new Employee(1, "Hem", "Developement"),
			new Employee(2, "Rakesh", "Account"),
			new Employee(3, "Mitesh", "Sales")));

	@Autowired
	private IEmployeeDAO daoRef;
	
	@Override
	public List<Employee> getAllEmployees() {
		//return empList;
		return daoRef.findAll();
	}

	@Override
	public Employee getEmployeeById(int empId) {
		//return empList.stream().filter(e -> e.getEmpId() == empId).findFirst().get();
		return daoRef.getOne(empId);
	}

	@Override
	public List<Employee> getEmployeeByFirstNameAndDeptName(String deptName,String empId) {
		//return empList.stream().filter(e -> e.getEmpId() == empId).findFirst().get();
		return daoRef.findByFirstNameAndDepartmentName(deptName,empId);
	}
	
	@Override
	public void addEmployee(Employee employee) {
		//empList.add(employee);
		daoRef.save(employee);
		
	}

	@Override
	public void updateEmployee(Employee employee,int empId) {
		/*for (int i=0; i<empList.size();i++) {
			Employee e = empList.get(i);
			if(e.getEmpId() == empId) {
				empList.set(i, employee);
				return;
			}
		}*/
		daoRef.save(employee);
	}

	@Override
	public void deleteEmployee(int empId) {
		//empList.removeIf(e -> e.getEmpId() == empId);
//		/daoRef.deleteById(empId);
	}
}
