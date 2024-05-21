package com.rihis.restController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.model.Plan;
import com.rihis.requestDto.PlanRequestDto;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.IPlanService;

@RestController
@RequestMapping("/api/v1/rihis/plan")
@CrossOrigin("http://localhost:4200/")
public class PlanController {
	
	@Autowired
	private IPlanService planService;
	
	@PostMapping("/createPlan")
	public ResponseEntity<ApiResponse> createPlan(@RequestBody PlanRequestDto dto){
		Plan createPlan = planService.createPlan(dto);
		ApiResponse response = new ApiResponse(201, "created", createPlan);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/getAllPlan")
	public ResponseEntity<ApiResponse> getAllPlans(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		Map<String, Object> allPlans = planService.getAllPlans(page, size);
		ApiResponse response = new ApiResponse(200, "success", allPlans);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/activateDeactivatePlans")
	public ResponseEntity<ApiResponse> activateDeactivatePlans(@RequestParam("planName") String planName){
		if(planService.activateDeactivatePlans(planName)) {
			ApiResponse response = new ApiResponse(200, "success", null);
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Internal Server Error", null));
	}
	
	@DeleteMapping("/deletePlan")
	public ResponseEntity<ApiResponse> deletePlan(@RequestParam("planName") String planName){
		if(planService.deletePlan(planName)) {
			ApiResponse response = new ApiResponse(200, "success", null);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error", null));
	}
	
	@GetMapping("/getPlan")
	public ResponseEntity<ApiResponse> getOnePlan(@RequestParam("planName") String planName){
		Plan onePlan = planService.getOnePlan(planName);
		ApiResponse response = new ApiResponse(200, "success", onePlan);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/updatePlan")
	public ResponseEntity<ApiResponse> updatePlan(@RequestBody Plan plan){
		Plan updatePlan = planService.updatePlan(plan);
		if(updatePlan != null) {
			ApiResponse response = new ApiResponse(200, "success", updatePlan);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error", null));
	}
	
	@GetMapping("/getActivePlans")
	public ResponseEntity<ApiResponse> getActivePlans(){
		List<Plan> activePlans = planService.getActivePlans();
		ApiResponse response = new ApiResponse(200, "success", activePlans);
		return ResponseEntity.ok(response);
	}
}
