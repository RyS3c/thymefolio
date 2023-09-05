package com.webapp.thymeleaf.thymefolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.thymeleaf.thymefolio.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
