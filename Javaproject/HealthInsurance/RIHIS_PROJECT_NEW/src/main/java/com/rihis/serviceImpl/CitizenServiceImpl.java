package com.rihis.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rihis.exception.ResourceAlreadyExist;
import com.rihis.exception.ResourceNotFoundException;
import com.rihis.model.Citizen;
import com.rihis.model.EligibilityDetermination;
import com.rihis.model.Kid;
import com.rihis.repository.CitizenRepository;
import com.rihis.repository.EligibilityDeterminationRepository;
import com.rihis.repository.PlanRepository;
import com.rihis.requestDto.CitizenRequestDto;
import com.rihis.responseDto.CitizenResponseDto;
import com.rihis.service.ICitizenService;

@Service
public class CitizenServiceImpl implements ICitizenService {

	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EligibilityDeterminationRepository determinationRepository;
	
	public Citizen createApplication(CitizenRequestDto dto) {
		Citizen citizen = new Citizen();
		citizen.setCitizenName(dto.getCitizenName());
		citizen.setEmail(dto.getEmail());
		citizen.setMobileNumber(dto.getMobileNumber());
		citizen.setDob(dto.getDob());
		citizen.setGender(dto.getGender());
		citizen.setSsnNumber(dto.getSsnNumber());
		citizen.setCreatedDate(LocalDate.now());
		citizen.setLastUpdatedDate(LocalDate.now());
		citizen.setApplicationId(new Random().nextInt(1000000,9999999));
		citizen.setCaseNumber(0);
		citizen.setStatus("PENDING");
		return citizenRepository.save(citizen);
	}


	public Citizen getApplicationByAppId(Integer applicationId) {
		return citizenRepository.findByApplicationId(applicationId).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
	}


	
	public Map<String, Object> getApplications(Integer page, Integer size) {
		Page<Citizen> applicationPage = citizenRepository.findAll(PageRequest.of(page, size,Sort.by(Direction.DESC,"createdDate")));
		return getMapObj(applicationPage);
	}
	
	public Map<String, Object> getMapObj(Page<Citizen> pageList){
		List<Citizen> list = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		List<CitizenResponseDto> responseDto = new ArrayList<>();
		list = pageList.getContent();
		for (Citizen citizen: list) {
			responseDto.add(getResponseDto(citizen));
		}
		
		
		response.put("citizens", list);
		response.put("totalPages", pageList.getTotalPages());
		response.put("totalCitizens", pageList.getTotalElements());
		response.put("currentPage", pageList.getNumber());
		return response;
	}
	
	public CitizenResponseDto getResponseDto(Citizen citizen) {
		CitizenResponseDto responseDto = new CitizenResponseDto();
		responseDto.setApplicationId(citizen.getApplicationId());
		responseDto.setCaseNumber(citizen.getCaseNumber());
		responseDto.setCitizenId(citizen.getCitizenId());
		responseDto.setCitizenName(citizen.getCitizenName());
		responseDto.setCreatedDate(citizen.getCreatedDate());
		responseDto.setDob(citizen.getDob());
		responseDto.setEmail(citizen.getEmail());
		responseDto.setGender(citizen.getGender());
		responseDto.setLastUpdatedDate(citizen.getLastUpdatedDate());
		responseDto.setMobileNumber(citizen.getMobileNumber());
		responseDto.setSsnNumber(citizen.getSsnNumber());
		return responseDto;
	}



	public boolean generateCaseNumber(Citizen citizen) {
		if(citizen.getCaseNumber() == 0||citizen.getCaseNumber() == null) {
			citizen.setCaseNumber(new Random().nextInt(10000000,99999999));
		}else {
			throw new ResourceAlreadyExist("CASENUMBER_ALREADY_GENERATED");
		}
		citizen.setStatus("PROCESSING");
		citizen.setLastUpdatedDate(LocalDate.now());
		Citizen save = citizenRepository.save(citizen);
		if(save != null)
			return true;
		return false;
	}


	
	public Citizen planSelectionForCitizen(CitizenRequestDto dto) {
		Citizen citizen = citizenRepository.findByCaseNumber(dto.getCaseNumber()).orElseThrow(()->new ResourceNotFoundException("DATA_NOT_FOUND"));
//		if(citizen.getPlan().getPlanName().equals(dto.getPlan().getPlanName())) {
//			throw new ResourceAlreadyExist("PLAN_ALREADY_SELECTED");
//		}
		citizen.setLastUpdatedDate(LocalDate.now());
		citizen.setStatus("PROCESSING");
		System.out.println(planRepository.findById(dto.getPlan().getPlanId()).get());
		citizen.setPlan(planRepository.findById(dto.getPlan().getPlanId()).get());
		return citizenRepository.save(citizen);
	}



	public Citizen addIncomeDetails(CitizenRequestDto dto) {
		Citizen citizen = citizenRepository.findByCaseNumber(dto.getCaseNumber()).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
//		if(citizen.getIncome() != null) {
//			throw new ResourceAlreadyExist("INCOME_DETAILS_ALREADY_EXIST");
//		}
		citizen.setLastUpdatedDate(LocalDate.now());
		citizen.setIncome(dto.getIncome());
		citizen.setStatus("PROCESSING");
		return citizenRepository.save(citizen);
	}


	public Citizen addEducationalDetails(CitizenRequestDto dto) {
		Citizen citizen = citizenRepository.findByCaseNumber(dto.getCaseNumber()).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
//		if(citizen.getEducationDetails()!= null) {
//			throw new ResourceAlreadyExist("EDUCATION_DETAILS_ALREADY_EXIST");
//		}
		citizen.setLastUpdatedDate(LocalDate.now());
		citizen.setStatus("PROCESSING");
		citizen.setEducationDetails(dto.getEducationDetails());
		return citizenRepository.save(citizen);
	}


	public Citizen addKidsDetails(CitizenRequestDto dto) {
		Citizen citizen = citizenRepository.findByCaseNumber(dto.getCaseNumber()).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		citizen.setLastUpdatedDate(LocalDate.now());
		citizen.setStatus("PROCESSING");
		citizen.setKids(dto.getKids());
		return citizenRepository.save(citizen);
	}



	public EligibilityDetermination eligibilityDetermination(Integer caseNumber) {
		
		Optional<EligibilityDetermination> findByCaseNumber = determinationRepository.findByCaseNumber(caseNumber);
		if(findByCaseNumber.isPresent())
			return findByCaseNumber.get();
		
		EligibilityDetermination determination = new EligibilityDetermination();
		
		Citizen citizen = citizenRepository.findByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		determination = setEligibilityDeterminationObj(citizen);
		determination.setStatus("REJECTED");
		if(citizen.getIncome()==null)
			throw new ResourceNotFoundException("INCOME_DATA_NOT_FOUND");
		if(citizen.getEducationDetails()==null)
			throw new ResourceNotFoundException("EDUCATIONAL_DATA_NOT_FOUND");
		if(citizen.getPlan()==null)
			throw new ResourceNotFoundException("PLAN_DATA_NOT_FOUND");
		
		if(citizen.getPlan().getPlanCategory().equals("SNAP")) {
			if(citizen.getIncome().getSalaryIncome() <= 300) {
				determination = setEligibilityDeterminationObj(citizen);
				determination.setBenefitAmount(150.0);
				determination.setStatus("APPROVED");
			}else {
				determination.setDenialReason("Employment Income More Then $300");
			}
		}
			
		if(citizen.getPlan().getPlanCategory().equals("CCAP")) {
			if(citizen.getIncome().getSalaryIncome() <= 300) {
				if(citizen.getKids().size() > 0) {
					if(!(kidsAgeFilter(citizen.getKids()).isEmpty())) {
						determination = setEligibilityDeterminationObj(citizen);
						determination.setBenefitAmount(200.0);
						determination.setStatus("APPROVED");
					}else {
						determination.setDenialReason("Kids Age Are Not Under 16years");
					}
				}else {
					determination.setDenialReason("No Kids Available");
				}
			}else {
				determination.setDenialReason("Employment Income More Then $300");
			}
		}
			
		if(citizen.getPlan().getPlanCategory().equals("MEDICAID")) {
			if(citizen.getIncome().getSalaryIncome() <= 300) {
				if((citizen.getIncome().getPropertyIncome()+citizen.getIncome().getRentIncome()) == 0) {
					determination = setEligibilityDeterminationObj(citizen);
					determination.setBenefitAmount(300.0);
					determination.setStatus("APPROVED");
				}else {
					determination.setDenialReason("You Have Property And Rent Income");
				}
			}else {
				determination.setDenialReason("Employment Income More Then $300");
			}
		}
			
		if(citizen.getPlan().getPlanCategory().equals("MEDICARE")) {
			if((LocalDate.now().getYear()-citizen.getDob().getYear()) >= 65) {
				determination = setEligibilityDeterminationObj(citizen);
				determination.setBenefitAmount(350.0);
				determination.setStatus("APPROVED");
			}else {
				determination.setDenialReason("Your Age Under 65");
			}
		}
			
		if(citizen.getPlan().getPlanCategory().equals("RIW")) {
			if(citizen.getIncome().getSalaryIncome() == 0) {
				if(citizen.getEducationDetails() != null) {
					determination = setEligibilityDeterminationObj(citizen);
					determination.setBenefitAmount(225.0);
					determination.setStatus("APPROVED");
				}else {
					determination.setDenialReason("You Are Not Graduated");
				}
			}else {
				determination.setDenialReason("You Are Not Un-Employed");
			}
		}
		
		if(determination.getStatus().equals("APPROVED")) {
			citizen.setLastUpdatedDate(LocalDate.now());
			citizen.setStatus("APPROVED");
		}else {
			citizen.setLastUpdatedDate(LocalDate.now());
			citizen.setStatus("REJECTED");
		}
		
		return determinationRepository.save(determination);
	}
	
	
	public List<Kid> kidsAgeFilter(List<Kid> kids){
		return kids.stream().filter(kid-> kid.getAge() <= 16).collect(Collectors.toList());
	}
	
	public void sendApprovedMail(String email,String planName) {
		String message = null;
		
		message = "<b><h1>Congratulation</h1></b><br>"+
						"You'r Eligible For This Plan:- <b>'"+planName+"'</b>";
		
		 emailService.sendEmail(message, "Approved", email);
	}
	
	public void sendRejectedMail(String email,String planName,String reason) {
		String message = null;
		
		message = "<b><h1>OOPS !!!</h1></b><br>"+
						"You'r Not Eligible For This Plan:- <b>'"+planName+"'</b><br>"+
				"<b>REASON:- </b> "+reason;
		
		 emailService.sendEmail(message, "Rejection", email);
	}
	
	public EligibilityDetermination setEligibilityDeterminationObj(Citizen citizen) {
		EligibilityDetermination detemination = new EligibilityDetermination();
		detemination.setHolderName(citizen.getCitizenName());
		detemination.setEmail(citizen.getEmail());
		detemination.setCaseNumber(citizen.getCaseNumber());
		detemination.setSsnNumber(citizen.getSsnNumber());
		detemination.setPlanName(citizen.getPlan().getPlanName());
		detemination.setStartDate(citizen.getPlan().getStartDate());
		detemination.setEndDate(citizen.getPlan().getEndDate());
		return detemination;
	}



	public Long getNumberOfApproved() {
		List<Citizen> findAllByStatus = citizenRepository.findAllByStatus("APPROVED");
		return (long)findAllByStatus.size();
	}



	public Long getNumberOfDeniedCitizens() {
		List<Citizen> findAllByStatus = citizenRepository.findAllByStatus("REJECTED");
		return (long)findAllByStatus.size();
	}
	
	
}
