package com.java.spring.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.spring.employee.dto.Employee;

@Repository
public interface IEmployeeDAO extends JpaRepository<Employee, Integer> {

	@Query(value = "SELECT * FROM hem.employee e WHERE e.dept_name=?1 AND e.emp_id=?2"  ,nativeQuery = true)
    List<Employee> findByFirstNameAndDepartmentName(String dept_name,String emp_id);
 
}
