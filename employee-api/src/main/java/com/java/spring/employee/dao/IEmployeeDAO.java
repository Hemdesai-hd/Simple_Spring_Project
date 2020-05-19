package com.java.spring.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.employee.dto.Employee;

@Repository
public interface IEmployeeDAO extends JpaRepository<Employee, Integer> {

}