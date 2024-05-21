package com.rihis.service;

import java.util.Map;

import com.rihis.model.EligibilityDetermination;

public interface IBenefitIssuanceService {

	public  Map<String, Object> getAllBenefitedCitizensList(Integer page,Integer size);
	
	public EligibilityDetermination getBenefitedCitizen(Integer caseNumber);
	
	public Long getAllBenefitedCitizens();
}
