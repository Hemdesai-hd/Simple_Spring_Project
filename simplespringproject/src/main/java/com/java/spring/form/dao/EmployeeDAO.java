package com.java.spring.form.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.java.spring.form.model.Employee;

@Repository
public class EmployeeDAO {

	private JdbcTemplate template;
	
	@Autowired
	public EmployeeDAO(DataSource dataSource) {
		template = new JdbcTemplate(dataSource);
	}
	
	public void saveEmployee(Employee e) {
				
				String sql="insert into employee_mst(firstName,lastName,freePasses,postalCode,email) values('"+e.getFirstName()+"','"+e.getLastName()+"','"+e.getFreePasses()+"','"+e.getPostalCode()+"','"+e.getEmail()+"')";
				System.out.println(sql);
			    template.update(sql);  
	}
	
	public List<Employee> getAllEmployees() {
		return template.query("select id,firstName,lastName,freePasses,postalCode,email from employee_mst",new ResultSetExtractor<List<Employee>>(){  
		    
		     public List<Employee> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		      
		        List<Employee> list=new ArrayList<Employee>();  
		        while(rs.next()){  
		        	Employee e=new Employee();  
		        e.setId(rs.getInt(1));  
		        e.setFirstName(rs.getString(2));  
		        e.setLastName(rs.getString(3));  
		        e.setFreePasses(rs.getInt(4));
		        e.setPostalCode(rs.getString(5));
		        e.setEmail(rs.getString(6));
		        list.add(e);
		        }  
		        return list;  
		        }  
		    });  
		  
		
		  }
	public Employee getEmployeeById(int empId) {
		return template.query("select id,firstName,lastName,freePasses,postalCode,email from employee_mst where id='"+empId+"'",new ResultSetExtractor<Employee>(){  
		    
		     public Employee extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		      
		        Employee e=new Employee();  
		        while(rs.next()){    
		        e.setId(rs.getInt(1));  
		        e.setFirstName(rs.getString(2));  
		        e.setLastName(rs.getString(3));  
		        e.setFreePasses(rs.getInt(4));
		        e.setPostalCode(rs.getString(5));
		        e.setEmail(rs.getString(6));
		        }  
		        return e;  
		        }  
		    });  
		  
		
		  }
	public boolean deleteEmployee(int empId) {
		String sql = "delete from employee_mst where id=?";
		return template.update(sql, empId) > 0;
	}

	public void updateEmployee(Employee e) {
		String sql = "update employee_mst set firstName='"+e.getFirstName()+"',lastName='"+e.getLastName()+"',freePasses='"+e.getFreePasses()+"',email='"+e.getEmail()+"',postalCode='"+e.getPostalCode()+"' where id='"+e.getId()+"'";
		template.update(sql);
	}

}
