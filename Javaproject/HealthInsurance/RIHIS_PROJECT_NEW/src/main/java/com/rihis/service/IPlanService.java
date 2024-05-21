package com.rihis.service;

import java.util.List;
import java.util.Map;

import com.rihis.model.Plan;
import com.rihis.requestDto.PlanRequestDto;

public interface IPlanService {

	
	public Plan createPlan(PlanRequestDto dto);
	
	public Map<String, Object> getAllPlans(Integer page,Integer size);
	
	public Boolean activateDeactivatePlans(String planName);
	
	public Boolean deletePlan(String planName);
	
	public Plan getOnePlan(String planName);
	
	public Plan updatePlan(Plan plan);
	
	public List<Plan> getActivePlans();
}
