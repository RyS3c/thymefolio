package com.webapp.thymeleaf.thymefolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webapp.thymeleaf.thymefolio.model.Employee;
import com.webapp.thymeleaf.thymefolio.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.empRepo.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		
		java.util.Optional<Employee> opt = empRepo.findById(id);
		
		Employee emp = null;
		
		if(opt.isPresent()) {
			emp = opt.get();
		}else {
			throw new RuntimeException("Emp not found for id :: " + id);
		}
		return emp;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.empRepo.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.empRepo.findAll(pageable);
	}
}
