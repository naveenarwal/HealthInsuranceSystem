package com.dollop.app.service;

import java.util.List;
import java.util.Optional;

import com.dollop.app.model.Employee;
import com.dollop.app.payload.EmployeeRequest;
import com.dollop.app.payload.EmployeeResponse;


public interface IEmployeeService {
	
	   public Employee save(EmployeeRequest emp); 
	   
	   public Employee update(EmployeeRequest emp,Integer id);
	   
	   public List<Employee> getAll();
	   
	   public Employee getEmployeeById(Integer id);
	   
	   public Boolean delete(Integer id);
	  
	 
	
	

}
