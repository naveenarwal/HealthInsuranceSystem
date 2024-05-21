package com.rihis.service;

import java.util.Map;

import com.rihis.model.Citizen;
import com.rihis.model.EligibilityDetermination;
import com.rihis.requestDto.CitizenRequestDto;

public interface ICitizenService {

	public Citizen createApplication(CitizenRequestDto dto);
	
	public Citizen getApplicationByAppId(Integer applicationId);
	
	public Map<String, Object> getApplications(Integer page,Integer size);
	
	public boolean generateCaseNumber(Citizen citizen);
	
	public Citizen planSelectionForCitizen(CitizenRequestDto dto);
	
	public Citizen addIncomeDetails(CitizenRequestDto dto);
	
	public Citizen addEducationalDetails(CitizenRequestDto dto);
	
	public Citizen addKidsDetails(CitizenRequestDto dto);
	
	public EligibilityDetermination eligibilityDetermination(Integer caseNumber);
	
	public Long getNumberOfApproved();
	
	public Long getNumberOfDeniedCitizens();
}
