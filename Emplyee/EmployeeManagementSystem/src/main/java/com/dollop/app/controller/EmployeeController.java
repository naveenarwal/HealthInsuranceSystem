package com.dollop.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.model.Employee;
import com.dollop.app.payload.EmployeeRequest;
import com.dollop.app.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {
	
	@Autowired
	private IEmployeeService service;
	
	
	@PostMapping("/save")
	public ResponseEntity<Employee> save(@RequestBody EmployeeRequest emp)
	{
		return new ResponseEntity<Employee>(this.service.save(emp),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@RequestBody EmployeeRequest emp,@PathVariable Integer id)

	{
		Employee employee = service.update(emp,id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
			
		//return new ResponseEntity<Employee>(this.service.save(emp),HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Employee>> getAll()
	{
		List<Employee> list=this.service.getAll();
		return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Integer id)
	{
		this.service.delete(id);
		return  new ResponseEntity<Boolean>(true,HttpStatus.OK);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		ResponseEntity<Employee> response = new ResponseEntity<Employee>(this.service.getEmployeeById(id),HttpStatus.OK);
		return response;
	}

}


