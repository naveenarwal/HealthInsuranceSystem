package com.rihis.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rihis.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{

	public Page<Plan> findAllByIsDeleted(boolean b, PageRequest of);
	
	public Optional<Plan> findByPlanNameAndIsDeleted(String planName,boolean delete);
	
	@Transactional
	@Modifying
	@Query("UPDATE Plan p SET p.isActive=:active WHERE p.planName=:planName")
	public int planActivateAndDeactivate(@Param("planName") String planName,@Param("active") boolean active);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Plan p SET p.isDeleted=1 WHERE p.planName=:planName")
	public int deletePlan(@Param("planName") String planName);
	
	public List<Plan> findAllByIsActiveAndIsDeleted(boolean active,boolean delete);

}
