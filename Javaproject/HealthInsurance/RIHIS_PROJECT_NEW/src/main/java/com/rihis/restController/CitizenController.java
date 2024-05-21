package com.rihis.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.model.Citizen;
import com.rihis.model.EligibilityDetermination;
import com.rihis.requestDto.CitizenRequestDto;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.ICitizenService;

@RestController
@RequestMapping("/api/v1/rihis/citizen")
@CrossOrigin("*")
public class CitizenController {

	@Autowired
	private ICitizenService citizenService;
	
	@PostMapping("/createApplication")
	public ResponseEntity<ApiResponse> createApplication(@RequestBody CitizenRequestDto dto){
		Citizen createApplication = citizenService.createApplication(dto);
		ApiResponse response = new ApiResponse(201, "created", createApplication);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/getApplications")
	public ResponseEntity<ApiResponse> getApplications(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		Map<String, Object> applications = citizenService.getApplications(page, size);
		ApiResponse response = new ApiResponse(200, "success", applications);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getCitizenByAppId")
	public ResponseEntity<ApiResponse> getCitizenByAppId(@RequestParam("AppId") Integer appId){
		Citizen applicationByAppId = citizenService.getApplicationByAppId(appId);
		ApiResponse response = new ApiResponse(200, "success", applicationByAppId);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/generateCaseNumber")
	public ResponseEntity<ApiResponse> generateCaseNumber(@RequestBody Citizen citizen){
		if(citizenService.generateCaseNumber(citizen)) {
			return ResponseEntity.ok(new ApiResponse(200, "success", null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error", null));
	}
	
	@PutMapping("/planSelection")
	public ResponseEntity<ApiResponse> planSelectionForCitizen(@RequestBody CitizenRequestDto dto){
		System.out.println(dto);
		Citizen citizen = citizenService.planSelectionForCitizen(dto);
		ApiResponse response = new ApiResponse(200, "success", citizen);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/addIncomeDetails")
	public ResponseEntity<ApiResponse> addIncomeDetails(@RequestBody CitizenRequestDto dto){
		Citizen addIncomeDetails = citizenService.addIncomeDetails(dto);
		ApiResponse response = new ApiResponse(200, "success", addIncomeDetails);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/addEducationDetails")
	public ResponseEntity<ApiResponse> addEducationalDetails(@RequestBody CitizenRequestDto dto){
		Citizen addEducationalDetails = citizenService.addEducationalDetails(dto);
		ApiResponse response = new ApiResponse(200, "success", addEducationalDetails);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/addKidsDetails")
	public ResponseEntity<ApiResponse> addKidsDetails(@RequestBody CitizenRequestDto dto){
		Citizen addKidsDetails = citizenService.addKidsDetails(dto);
		ApiResponse response = new ApiResponse(200, "Success", addKidsDetails);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/eligibilityDetermination")
	public ResponseEntity<ApiResponse> eligibilityDetermination(@RequestParam("caseNumber") Integer caseNumber){
		EligibilityDetermination eligibilityDetermination = citizenService.eligibilityDetermination(caseNumber);
		ApiResponse response = new ApiResponse(200, "success", eligibilityDetermination);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getNumberOfApprovedCitizen")
	public ResponseEntity<ApiResponse> getNumberOfApprovedCitizen(){
		Long numberOfApproved = citizenService.getNumberOfApproved();
		ApiResponse response = new ApiResponse(200, "success", numberOfApproved);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getNumberOfRejectedCitizen")
	public ResponseEntity<ApiResponse> getNumberOfRejectedCitizen(){
		Long numberOfDenied = citizenService.getNumberOfDeniedCitizens();
		ApiResponse response = new ApiResponse(200, "success", numberOfDenied);
		return ResponseEntity.ok(response);
	}
}
