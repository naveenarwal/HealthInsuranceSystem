package com.dollop.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.model.Employee;
import com.dollop.app.payload.EmployeeRequest;
import com.dollop.app.repo.EmployeeRepository;
import com.dollop.app.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repo;
	@Override
	public Employee save(EmployeeRequest emp) {
		// TODO Auto-generated method stub
		
		Employee employee=new Employee();
		employee.setName(emp.getName());
		employee.setAddress(emp.getAddress());
		employee.setContact(emp.getContact());
		return this.repo.save(employee);
	}

	@Override
	public Employee update(EmployeeRequest emp,Integer id) {
		// TODO Auto-generated method stub
		Optional<Employee> optionalEmployee = repo.findById(id);
		 if (optionalEmployee.isPresent()) {
		Employee employee=new Employee();
		employee.setId(emp.getId());
		employee.setName(emp.getName());
		employee.setAddress(emp.getAddress());
		employee.setContact(emp.getContact());
		return this.repo.save(employee);
		 }
		 else {
			 return null;
		 }
	}
	
	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		
		List<Employee> list=this.repo.findAll();
		return list;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		
		Employee employee = this.repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with id : " + id));
		employee.setId(id);
		this.repo.delete(employee);
		return true;
	}
			
	@Override
	public Employee getEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id).get();
	}

}
