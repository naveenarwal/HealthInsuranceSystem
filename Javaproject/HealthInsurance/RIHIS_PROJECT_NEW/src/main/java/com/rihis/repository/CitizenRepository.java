package com.rihis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rihis.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer>{
	
	public Optional<Citizen> findByApplicationId(Integer applicationId);
	
	public Optional<Citizen> findByCaseNumber(Integer caseNumber);
	
	public List<Citizen> findAllByStatus(String status);

}
