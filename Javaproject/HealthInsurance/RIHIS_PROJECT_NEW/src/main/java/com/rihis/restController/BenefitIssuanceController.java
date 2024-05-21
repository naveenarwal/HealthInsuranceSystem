package com.rihis.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.model.EligibilityDetermination;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.IBenefitIssuanceService;

@RestController
@RequestMapping("/api/v1/rihis/benefitIssuance")
@CrossOrigin("http://localhost:4200/")
public class BenefitIssuanceController {

	@Autowired
	private IBenefitIssuanceService benefitIssuanceService;
	
	@GetMapping("/getAllBenefitedCitizens")
	public ResponseEntity<ApiResponse> getAllBenefitedCitizens(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		Map<String, Object> benefitedCitizensList = benefitIssuanceService.getAllBenefitedCitizensList(page, size);
		ApiResponse response = new ApiResponse(200, "success", benefitedCitizensList);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getBenefitedCitizen")
	public ResponseEntity<ApiResponse> getBenefitedCitizen(@RequestParam("caseNumber") Integer caseNumer){
		EligibilityDetermination benefitedCitizen = benefitIssuanceService.getBenefitedCitizen(caseNumer);
		ApiResponse response = new ApiResponse(200, "success", benefitedCitizen);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getNumberOfBenefitedCitizens")
	public ResponseEntity<ApiResponse> getNumberOfBenefitedCititzens(){
		Long allBenefitedCitizens = benefitIssuanceService.getAllBenefitedCitizens();
		ApiResponse response = new ApiResponse(200, "success", allBenefitedCitizens);
		return ResponseEntity.ok(response);
	}
}
