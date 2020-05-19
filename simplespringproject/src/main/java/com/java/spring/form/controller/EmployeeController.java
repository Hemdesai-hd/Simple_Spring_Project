package com.java.spring.form.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

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
        List<Employee> list=employeeDao.getAllEmployees();
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
}
