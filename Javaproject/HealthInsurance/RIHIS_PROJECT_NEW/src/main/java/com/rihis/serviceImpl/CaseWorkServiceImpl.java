package com.rihis.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rihis.exception.UserNotFoundException;
import com.rihis.exception.UsernameAlreadyExist;
import com.rihis.model.CaseWorker;
import com.rihis.repository.CaseWorkerRepository;
import com.rihis.requestDto.CaseWorkerRequestDto;
import com.rihis.responseDto.CaseWorkerResponseDto;
import com.rihis.service.ICaseWorkerSevice;
import com.rihis.util.JwtUtil;

@Service
public class CaseWorkServiceImpl implements ICaseWorkerSevice,UserDetailsService {

	@Autowired
	private CaseWorkerRepository caseWorkerRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CaseWorker caseWorker = caseWorkerRepository.findByEmailAndIsActiveAndIsDeleted(username,true,false).orElseThrow(
							()-> new UsernameNotFoundException("Username '"+username+"' Not Found"));
		List<GrantedAuthority> authority = caseWorker.getRoles().stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return new User(username, caseWorker.getPassword(),authority) ;
	}

	
	public CaseWorker createUser(CaseWorkerRequestDto requestDto) {
		Optional<CaseWorker> findByEmail = caseWorkerRepository.findByEmailAndIsDeleted(requestDto.getEmail(),false);
		if(findByEmail.isPresent())
			throw new UsernameAlreadyExist("USERNAME_ALREADY_EXIST !!!");
		
		CaseWorker caseWorker = new CaseWorker();
		caseWorker.setCaseWorkerName(requestDto.getCaseWorkerName());
		caseWorker.setEmail(requestDto.getEmail());
		caseWorker.setMobileNumber(requestDto.getMobileNumber());
		caseWorker.setDob(requestDto.getDob());
		caseWorker.setSsnNumber(requestDto.getSsnNumber());
		caseWorker.setCreatedDate(LocalDate.now());
		caseWorker.setLastUpdatedDate(LocalDate.now());
		caseWorker.setGender(requestDto.getGender());
		caseWorker.setRoles(List.of("CASE_WORKER"));
		//caseWorker.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		CaseWorker saveWorker = caseWorkerRepository.save(caseWorker);
		if(saveWorker != null) {
			emailService.sendEmail("Click to the given link and create password"+
					"<b><a href=\"http://localhost:4200/changePassword\">Click Here To Generate Password</a><b>",
					"Generate Password", saveWorker.getEmail());
			return saveWorker;
		}
		return null;
	}


	public Boolean generatePassword(String password,String email) {
		int generatePassword = caseWorkerRepository.generatePassword(passwordEncoder.encode(password), email);
		if(generatePassword!=0) {
			emailService.sendEmail("<b>Password Generate Successfully<b>", "GENERATE PASSWORD", email);
			return true;
		}
		return false;
	}

	//@CachePut(value = "CaseWorker",key="#caseWorkerId0",condition = "#caseWorkerId0!=null")
	public Boolean accountActivateAndDeactivate(String username) {
		CaseWorker findByEmailAndIsDeleted = caseWorkerRepository.findByEmailAndIsDeleted(username, false).get();
		System.out.println(findByEmailAndIsDeleted.getCaseWorkerName());
		int check = 0;
		if(findByEmailAndIsDeleted.isActive() == true) {
			check =	caseWorkerRepository.accountActivateAndDeactivate(username,false);
		}
		else {
			check = caseWorkerRepository.accountActivateAndDeactivate(username,true);
		}
		
		if(check !=0)
			return true;
		else 
			return false;
		
	}


	//@CacheEvict(value = "CaseWorker",allEntries = true)
	public Boolean deleteAccounts(String username)  {
		int deleteAccount = caseWorkerRepository.deleteAccount(username);
		if(deleteAccount != 0)
			return true;
		return false;
	}

	//@Cacheable(value = "CaseWorkerResponseDto" )
	public Map<String, Object> viewAccounts(Integer page,Integer size) {
		Page<CaseWorker> pageList = caseWorkerRepository.findAllByRolesAndIsDeleted("CASE_WORKER",false,PageRequest.of(page, size,Sort.by(Direction.ASC, "caseWorkerId")));
		return getMapObj(pageList);
	}
	
	
	public Map<String, Object> getMapObj(Page<CaseWorker> pageList){
		List<CaseWorker> caseWorkerList = new ArrayList<>();
		List<CaseWorkerResponseDto> responseDtoList = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		caseWorkerList = pageList.getContent();
		
		for (CaseWorker worker: caseWorkerList) {
			responseDtoList.add(getResponseObj(worker));
		}
		
		response.put("caseWorkers", responseDtoList);
		response.put("totalPages", pageList.getTotalPages());
		response.put("currentPage", pageList.getNumber());
		response.put("totalCaseWorker", pageList.getTotalElements());
		
		return response;
	}
	
	
	public CaseWorkerResponseDto getResponseObj(CaseWorker worker) {
		CaseWorkerResponseDto dto = new CaseWorkerResponseDto();
		dto.setCaseWorkerId(worker.getCaseWorkerId());
		dto.setCaseWorkerName(worker.getCaseWorkerName());
		dto.setEmail(worker.getEmail());
		dto.setDob(worker.getDob());
		dto.setCreatedDate(worker.getCreatedDate());
		dto.setGender(worker.getGender());
		dto.setMobileNumber(worker.getMobileNumber());
		dto.setSsnNumber(worker.getSsnNumber());
		dto.setActive(worker.isActive());
		dto.setRoles(worker.getRoles());
		
		return dto;
	}
	
	public CaseWorker getCurrentUser(String token) {
		return caseWorkerRepository.findByEmailAndIsActiveAndIsDeleted(jwtUtil.getUsername(token), true, false).get();
	}


	
	public CaseWorker getCaseWorker(String email) {
		return caseWorkerRepository.findByEmailAndIsDeleted(email, false).orElseThrow(()-> new UserNotFoundException("USER_NOT_FOUND"));
	}


	public CaseWorker updateCaseWorker(CaseWorker worker) {
		worker.setLastUpdatedDate(LocalDate.now());
		return caseWorkerRepository.save(worker);
	}
	
	

}
