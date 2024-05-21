package com.rihis.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rihis.model.Citizen;
import com.rihis.repository.CitizenRepository;
import com.rihis.repository.PlanRepository;
import com.rihis.requestDto.FilterRequestDto;
import com.rihis.service.IReportsService;

@Service
public class ReportsServiceImpl implements IReportsService{

	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	
	public Map<String, Object> getFilterData(FilterRequestDto dto,Integer page,Integer size) {
		Citizen citizen = new Citizen();
		
		if(Objects.nonNull(dto.getPlanId())) {
			citizen.setPlan(planRepository.findById(dto.getPlanId()).get());
		}
		
		if(Objects.nonNull(dto.getGender())) {
			citizen.setGender(dto.getGender());
		}
		
		if(Objects.nonNull(dto.getStatus())) {
			citizen.setStatus(dto.getStatus());
		}
		
		
		Example<Citizen> example = Example.of(citizen);
		System.out.println(example);
		Page<Citizen> pageData = citizenRepository.findAll(example,PageRequest.of(page, size,Sort.by(Direction.ASC, "citizenId")));
		System.out.println(pageData);
		
		return getMapObj(pageData);
	}
	
	public Map<String, Object> getMapObj(Page<Citizen> pageList){
		List<Citizen> list = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		list = pageList.getContent();
		
		response.put("reports", list);
		response.put("totalReports", pageList.getTotalElements());
		response.put("totalPages", pageList.getTotalPages());
		response.put("currentPage", pageList.getNumber());
		return response;
	}
	

}
