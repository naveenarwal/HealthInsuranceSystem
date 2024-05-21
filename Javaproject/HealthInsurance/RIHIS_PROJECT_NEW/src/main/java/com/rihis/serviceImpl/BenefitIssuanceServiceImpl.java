package com.rihis.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rihis.exception.ResourceNotFoundException;
import com.rihis.model.EligibilityDetermination;
import com.rihis.repository.EligibilityDeterminationRepository;
import com.rihis.service.IBenefitIssuanceService;

@Service
public class BenefitIssuanceServiceImpl implements IBenefitIssuanceService{

	
	@Autowired
	private EligibilityDeterminationRepository determinationRepository;
	
	
	
	public  Map<String, Object> getAllBenefitedCitizensList(Integer page,Integer size) {
		Page<EligibilityDetermination> pageData = determinationRepository.findAllByBenefitCitizens(PageRequest.of(page, size,Sort.by(Direction.ASC, "benefitAmount")));
		List<EligibilityDetermination> listData = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		listData = pageData.getContent();
		
		response.put("benefitCitizens", listData);
		response.put("totalBenefitCitizens", pageData.getTotalElements());
		response.put("totalPages", pageData.getTotalPages());
		response.put("currentPage", pageData.getNumber());
		return response; 
	}

	public EligibilityDetermination getBenefitedCitizen(Integer caseNumber) {
		return determinationRepository.getBenefitedCitizenByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
	}

	
	public Long getAllBenefitedCitizens() {
		Page<EligibilityDetermination> findAllByBenefitCitizens = determinationRepository.findAllByBenefitCitizens(PageRequest.of(0,Integer.MAX_VALUE));
		return findAllByBenefitCitizens.getTotalElements();
	}
}
