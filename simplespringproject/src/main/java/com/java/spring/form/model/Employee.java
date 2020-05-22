package com.java.spring.form.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.java.spring.form.annotations.OwnAnnotation;

public class Employee {

	private Integer id;
	private String firstName;
	
	@NotNull(message = "is required")
	@Size(min = 1,message = "is required")
	private String lastName;
	
	@NotNull(message = "is required")
	@Min(value = 0,message = "must be greater than or equal to 0")
	@Max(value = 10,message = "must be less than or equal to 10")
	private Integer freePasses;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{5}",message = "only 5 char/digits")
	private String postalCode;
	
	@NotNull(message = "is required")
	@Email(message = "Invalid email! Please enter valid email")
	@OwnAnnotation(message = "length should be less than 30")
	private String email;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getFreePasses() {
		return freePasses;
	}
	public void setFreePasses(Integer freePasses) {
		this.freePasses = freePasses;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", freePasses="
				+ freePasses + ", postalCode=" + postalCode + ", email=" + email + "]";
	}
	
	
}
