package com.rihis.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rihis.exception.ResourceAlreadyExist;
import com.rihis.exception.ResourceNotFoundException;
import com.rihis.model.Citizen;
import com.rihis.model.CorrespondenceTrigger;
import com.rihis.model.EligibilityDetermination;
import com.rihis.repository.CitizenRepository;
import com.rihis.repository.CorrespondenceRepository;
import com.rihis.repository.EligibilityDeterminationRepository;
import com.rihis.service.ICitizenService;
import com.rihis.service.ICorrespondenceService;

@Service
public class CorrespondenceServiceImpl implements ICorrespondenceService {

	@Autowired
	private EligibilityDeterminationRepository determinationRepository;
	
	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private CorrespondenceRepository correspondenceRepository;
	
	@Autowired
	private CitizenServiceImpl citizenServiceImpl;
	
	public CorrespondenceTrigger generateCorrespondence(Integer caseNumber) {
		Optional<CorrespondenceTrigger> findByCaseNumber = correspondenceRepository.findByCaseNumber(caseNumber);
		if(findByCaseNumber.isPresent()) {
			throw new ResourceAlreadyExist("CORRESPONDENCE_ALREADY_GENERATED");
		}
		
		CorrespondenceTrigger trigger = new CorrespondenceTrigger();
		Optional<Citizen> citizen = citizenRepository.findByCaseNumber(caseNumber);
		if(citizen.isPresent()) {
			trigger.setCaseNumber(caseNumber);
			trigger.setCoPdf(null);
		}
		return correspondenceRepository.save(trigger);
	}
 
	public Map<String, Object> getCorrensponence(Integer page,Integer size) {
		Page<CorrespondenceTrigger> pageList = correspondenceRepository.findAll(PageRequest.of(page, size , Sort.by(Direction.ASC, "coTriggerId")));
		return getMapObj(pageList);
	}
	
	public Map<String, Object> getMapObj(Page<CorrespondenceTrigger> pageList){
		List<CorrespondenceTrigger> list = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		list = pageList.getContent();
		
		response.put("correspondence", list);
		response.put("totalCorrespondence", pageList.getTotalElements());
		response.put("totalPages", pageList.getTotalPages());
		response.put("currentPage", pageList.getNumber());
		return response;
	}


	
	public Map<String, Object> getApprovedAndPendingCorrensponence(Integer page, Integer size, String status) {
		Page<CorrespondenceTrigger> pageList = correspondenceRepository.findAllByNoticeStatus(status,PageRequest.of(page, size, Sort.by(Direction.ASC, "coTriggerId")));
		return getMapObj(pageList);
	}


	
	public boolean sendNoticeToApplicant(Integer caseNumber) {
		EligibilityDetermination eligibilityDetermination = determinationRepository.findByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		if(eligibilityDetermination.getStatus().equals("APPROVED")) {
			citizenServiceImpl.sendApprovedMail(eligibilityDetermination.getEmail(), eligibilityDetermination.getPlanName());
			correspondenceRepository.updateStatus(caseNumber);
			return true;
		}
		if(eligibilityDetermination.getStatus().equals("REJECTED")){
			citizenServiceImpl.sendRejectedMail(eligibilityDetermination.getEmail(), eligibilityDetermination.getPlanName(), eligibilityDetermination.getDenialReason());
			correspondenceRepository.updateStatus(caseNumber);
			return true;
		}
		
		return false;
	}
	


}
