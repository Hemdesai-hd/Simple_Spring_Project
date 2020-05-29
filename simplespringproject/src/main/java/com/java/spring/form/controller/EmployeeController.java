package com.java.spring.form.controller;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.DocumentException;
import com.java.spring.form.dao.EmployeeDAO;
import com.java.spring.form.model.Employee;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor editor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, editor);
	}
	
	@Autowired
	private EmployeeDAO employeeDao;
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/showForm")
	public String showForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "employee-form";
	}
	
	@RequestMapping("/processForm")
	public String processForm(@Valid @ModelAttribute("employee") Employee employee,BindingResult bindingResult,Model mode) {
		if(bindingResult.hasErrors()) {
			return "employee-form";
		}else {
			employeeDao.saveEmployee(employee);
			return "redirect:/employee/viewemployees";
		}
		
	}
	
	@RequestMapping("/viewemployees")  
    public ModelAndView viewEmployees(){  
        List<Employee> list = employeeDao.getAllEmployees();
        ModelAndView mv = new ModelAndView("employee-confirmation");
        mv.addObject("employee", list);
        return mv;
    }
	
	@RequestMapping("/updateEmployeeData")  
    public ModelAndView getEmployeeById(@RequestParam("employeeId") int empId){  
        Employee list=employeeDao.getEmployeeById(empId);
        System.out.println("getbyid:"+list);
        ModelAndView mv = new ModelAndView("employee-edit");
        mv.addObject("employee", list);
        return mv;
    }
	
	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam("employeeId") int empId) {
		employeeDao.deleteEmployee(empId);
		return "redirect:/employee/viewemployees";
    }
	
	@RequestMapping("/updateEmployee")
	public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,BindingResult bindingResult,Model mode) {
		if(bindingResult.hasErrors()) {
			return "employee-form";
		}else {
			employeeDao.updateEmployee(employee);
			return "redirect:/employee/viewemployees";
		}
		
	}
	@RequestMapping("/generatePdf")
	public String generatePdfReport() throws FileNotFoundException, DocumentException, MalformedURLException {
//		if(bindingResult.hasErrors()) {
//			return "employee-form";
//		}else {
			List<Employee> list=employeeDao.getAllEmployees();
			employeeDao.generatePdf(list);
			employeeDao.generatePdf1();
			return "redirect:/employee/viewemployees";
//		}
		
	}
}
