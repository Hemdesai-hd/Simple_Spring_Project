package com.java.spring.form.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.java.spring.form.model.Employee;

@Repository
public class EmployeeDAO {

	private JdbcTemplate template;
	private JdbcTemplate template2;

	@Autowired
	public EmployeeDAO(@Qualifier("mysql-database") DataSource dataSource,@Qualifier("oracle-database") DataSource dataSource2) {
		template = new JdbcTemplate(dataSource);
		template2 = new JdbcTemplate(dataSource2);
	}

	public void saveEmployee(Employee e) {

		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection().prepareStatement(
					"insert into employee_mst(firstName,lastName,freePasses,postalCode,email) values(?,?,?,?,?)");
			st.setString(1, e.getFirstName());
			st.setString(2, e.getLastName());
			st.setInt(3, e.getFreePasses());
			st.setString(4, e.getPostalCode());
			st.setString(5, e.getEmail());

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if(i > 1) {
				template.getDataSource().getConnection().commit();
			}else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//				String sql="insert into employee_mst(firstName,lastName,freePasses,postalCode,email) values('"+e.getFirstName()+"','"+e.getLastName()+"','"+e.getFreePasses()+"','"+e.getPostalCode()+"','"+e.getEmail()+"')";
//				System.out.println(sql);
//			    template.update(sql);
	}

	public List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection()
					.prepareStatement("select id,firstName,lastName,freePasses,postalCode,email from employee_mst");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setFirstName(rs.getString(2));
				e.setLastName(rs.getString(3));
				e.setFreePasses(rs.getInt(4));
				e.setPostalCode(rs.getString(5));
				e.setEmail(rs.getString(6));
				list.add(e);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;

	}

	public Employee getEmployeeById(int empId) {
		return template.query(
				"select id,firstName,lastName,freePasses,postalCode,email from employee_mst where id='" + empId + "'",
				new ResultSetExtractor<Employee>() {

					public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {

						Employee e = new Employee();
						while (rs.next()) {
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

	public void deleteEmployee(int empId) {
//		String sql = "delete from employee_mst where id=?";
//		return template.update(sql, empId) > 0;
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection()
					.prepareStatement("delete from employee_mst where id=?");
			st.setInt(1, empId);

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if(i > 1) {
				template.getDataSource().getConnection().commit();
			}else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void updateEmployee(Employee e) {
//		String sql = "update employee_mst set firstName='"+e.getFirstName()+"',lastName='"+e.getLastName()+"',freePasses='"+e.getFreePasses()+"',email='"+e.getEmail()+"',postalCode='"+e.getPostalCode()+"' where id='"+e.getId()+"'";
//		template.update(sql);
		try {
			PreparedStatement st = (PreparedStatement) template.getDataSource().getConnection().prepareStatement(
					"update employee_mst set firstName=?,lastName=?,freePasses=?,email=?,postalCode=? where id=?");
			st.setString(1, e.getFirstName());
			st.setString(2, e.getLastName());
			st.setInt(3, e.getFreePasses());
			st.setString(4, e.getPostalCode());
			st.setString(5, e.getEmail());
			st.setInt(6, e.getId());

			int i = st.executeUpdate();
			template.getDataSource().getConnection().setAutoCommit(false);
			if(i > 1) {
				template.getDataSource().getConnection().commit();
			}else {
				template.getDataSource().getConnection().rollback();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
