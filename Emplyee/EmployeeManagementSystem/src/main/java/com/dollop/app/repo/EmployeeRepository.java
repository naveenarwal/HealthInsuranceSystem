package com.dollop.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
