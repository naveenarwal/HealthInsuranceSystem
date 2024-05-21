package com.rihis.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.model.CorrespondenceTrigger;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.ICorrespondenceService;


@RestController
@RequestMapping("/api/v1/rihis/correspondence")
@CrossOrigin("http://localhost:4200/")
public class CorrespondenceTriggerController {

	@Autowired
	private ICorrespondenceService correspondenceService;
	
	@PostMapping("/generateCorrespondence")
	public ResponseEntity<ApiResponse> generateCorrespondence(@RequestParam("caseNumber") Integer caseNumber){
		CorrespondenceTrigger correspondence = correspondenceService.generateCorrespondence(caseNumber);
		ApiResponse response = new ApiResponse(201, "created", correspondence);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/getCorrespondence")
	public ResponseEntity<ApiResponse> getCorrespondence(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		Map<String, Object> corrensponence = correspondenceService.getCorrensponence(page,size);
		ApiResponse response = new ApiResponse(200, "success", corrensponence);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getApprovedAndPendingCorrespondence")
	public ResponseEntity<ApiResponse> getPendingCorrespondence(@RequestParam("page") Integer page, @RequestParam("size") Integer size,@RequestParam("status") String status){
		Map<String, Object> pendingCorrensponence = correspondenceService.getApprovedAndPendingCorrensponence(page, size ,status);
		ApiResponse response = new ApiResponse(200, "Success", pendingCorrensponence);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/sendNotice")
	public ResponseEntity<ApiResponse> sendNoticeToApplicant(@RequestParam("caseNumber")Integer caseNumber){
		boolean sendNoticeToApplicant = correspondenceService.sendNoticeToApplicant(caseNumber);
		ApiResponse response = new ApiResponse(200, "success", sendNoticeToApplicant);
		return ResponseEntity.ok(response);
	}
	
}

