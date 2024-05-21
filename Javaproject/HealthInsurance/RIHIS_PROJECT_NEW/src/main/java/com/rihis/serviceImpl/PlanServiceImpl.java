package com.rihis.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rihis.exception.ResourceNotFoundException;
import com.rihis.model.Plan;
import com.rihis.repository.PlanRepository;
import com.rihis.requestDto.PlanRequestDto;
import com.rihis.responseDto.PlanResponseDto;
import com.rihis.service.IPlanService;

@Service
public class PlanServiceImpl implements IPlanService {

	@Autowired
	private PlanRepository planRepository;
	
	public Plan createPlan(PlanRequestDto dto) {
		Plan plan = new Plan();
		plan.setPlanName(dto.getPlanName());
		plan.setStartDate(dto.getStartDate());
		plan.setEndDate(dto.getEndDate());
		plan.setCreatedDate(LocalDate.now());
		plan.setLastUpdatedDate(LocalDate.now());
		plan.setPlanCategory(dto.getPlanCategory());
		plan.setIsActive(false);
		
		return planRepository.save(plan);
	}


	public Map<String, Object> getAllPlans(Integer page, Integer size) {
		Page<Plan> planPage = planRepository.findAllByIsDeleted(false,PageRequest.of(page, size));
		return getMapObj(planPage);
	}
	
	public Map<String, Object> getMapObj(Page<Plan> planPage){
		List<Plan> planList = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		List<PlanResponseDto> responseDto = new ArrayList<>();
		
		planList = planPage.getContent();
		
		for (Plan plan : planList) {
			responseDto.add(getResponseObj(plan));
		}
		
		response.put("plans", responseDto);
		response.put("totalPages", planPage.getTotalPages());
		response.put("currentPage", planPage.getNumber());
		response.put("totalPlans", planPage.getTotalElements());
		
		return response;
		
	}
	
	public PlanResponseDto getResponseObj(Plan plan) {
		PlanResponseDto dto = new PlanResponseDto();
		dto.setPlanId(plan.getPlanId());
		dto.setPlanName(plan.getPlanName());
		dto.setStartDate(plan.getStartDate());
		dto.setEndDate(plan.getEndDate());
		dto.setPlanCategory(plan.getPlanCategory());
		dto.setIsActive(plan.getIsActive());
		dto.setIsDeleted(plan.getIsDeleted());
		return dto;
	}


	
	public Boolean activateDeactivatePlans(String planName) {
		Plan plan = planRepository.findByPlanNameAndIsDeleted(planName, false).orElseThrow(()->new ResourceNotFoundException("DATA_NOT_FOUND"));
		
		int check = 0;
		
		if(plan.getIsActive()==true) {
			check = planRepository.planActivateAndDeactivate(planName,false);
		}else {
			check = planRepository.planActivateAndDeactivate(planName,true);
		}
		
		if(check !=0)
			return true;
		else 
			return false;
	}


	
	public Boolean deletePlan(String planName) {
		int deletePlan = planRepository.deletePlan(planName);
		if(deletePlan != 0)
			return true;
		return false;
	}


	public Plan getOnePlan(String planName) {
		return planRepository.findByPlanNameAndIsDeleted(planName, false).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));	 
	}


	public Plan updatePlan(Plan plan) {
		plan.setLastUpdatedDate(LocalDate.now());
		return planRepository.save(plan);
	}


	public List<Plan> getActivePlans() {
		 return planRepository.findAllByIsActiveAndIsDeleted(true,false);
	}

}
