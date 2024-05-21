package com.rihis.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.model.CaseWorker;
import com.rihis.requestDto.CaseWorkerRequestDto;
import com.rihis.requestDto.JwtRequestDto;
import com.rihis.responseDto.ApiResponse;
import com.rihis.serviceImpl.CaseWorkServiceImpl;
import com.rihis.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/rihis/caseWorker")
@CrossOrigin("http://localhost:4200/")
public class CaseWorkerController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CaseWorkServiceImpl workerSevice;
	
//	@Autowired
//	private 
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("hello rahul");
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> loginUser(@RequestBody JwtRequestDto dto){
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		String token = jwtUtil.generateToken(dto.getEmail());
		ApiResponse response = new ApiResponse(200, "success", token);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/createCaseWorker")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CaseWorkerRequestDto dto ){
		CaseWorker worker = workerSevice.createUser(dto);
		ApiResponse response = new ApiResponse(201, "created", worker);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/generatePassword")
	public ResponseEntity<ApiResponse> generatePassword(@RequestParam("email") String email,@RequestParam("password") String password){
		System.out.println(email+" "+password);
		Boolean generatePassword = workerSevice.generatePassword(password,email);
		if(generatePassword)
			return ResponseEntity.ok(new ApiResponse(200,"success","Password Generate Successfully"));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Something went wrong!!", null));
	}
	
	@PutMapping("/caseWorkerAccountsActivateDeactivate")
	public ResponseEntity<ApiResponse> accountActivateAndDeactivate(@RequestParam("email") String username){
		System.out.println("hello "+username);
		if(workerSevice.accountActivateAndDeactivate(username)) {
			return ResponseEntity.ok(new ApiResponse(200, "success", null));
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error", null));
		}
	}
	
	@DeleteMapping("/caseWorkerAccountDelete")
	public ResponseEntity<ApiResponse> deleteAccounts(@RequestParam("email") String username){
		if(workerSevice.deleteAccounts(username)) {
				ApiResponse response = new ApiResponse(200, "Delete Successfully", null);
				return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error", null));
	}
	
	@GetMapping("/viewAccounts")
	public ResponseEntity<ApiResponse> viewAccounts(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		System.out.println(page+" "+size);
		Map<String, Object> allAccounts = workerSevice.viewAccounts(page,size);
		ApiResponse response = new ApiResponse(200, "success", allAccounts);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/currentUser/{token}")
	public ResponseEntity<ApiResponse> getLoginUser(@PathVariable("token") String token){
		ApiResponse response = new ApiResponse(200, "success", workerSevice.getCurrentUser(token));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getCaseWorker")
	public ResponseEntity<ApiResponse> getCaseWorker(@RequestParam("email") String email){
		CaseWorker caseWorker = workerSevice.getCaseWorker(email);
		ApiResponse response = new ApiResponse(200, "success", caseWorker);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateCaseWorker")
	public ResponseEntity<ApiResponse> updateCaseWorker(@RequestBody CaseWorker worker){
		CaseWorker updateCaseWorker = workerSevice.updateCaseWorker(worker);
		if(updateCaseWorker != null) {
			ApiResponse response = new ApiResponse(200, "success", updateCaseWorker);
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Internal Server Error!", null));
	}
}
