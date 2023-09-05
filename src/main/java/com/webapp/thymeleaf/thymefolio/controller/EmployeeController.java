package com.webapp.thymeleaf.thymefolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.webapp.thymeleaf.thymefolio.model.Employee;
import com.webapp.thymeleaf.thymefolio.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1,"firstName", "asc", model);
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);

		return "new_employee";				
	}
	
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		empService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value = "id") long id, Model model) {
		Employee emp = empService.getEmployeeById(id);
		
		model.addAttribute("employee", emp);
		
		return "update_employee";
				
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		this.empService.deleteEmployeeById(id);
		
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, 
								@RequestParam("sortField") String sortField, 
								@RequestParam("sortDir") String sortDirection, Model model) {
		
		int pageSize = 5;
		
		Page<Employee> page = empService.findPaginated(pageNo, pageSize, sortField, sortDirection);
		
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listEmployees", listEmployees);
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDirection);
		model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
		
		return "index";
	}
	
}
