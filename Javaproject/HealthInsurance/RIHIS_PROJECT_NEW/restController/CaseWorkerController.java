package com.rihis.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.jwtUtil.JwtUtil;
import com.rihis.model.CaseWorker;
import com.rihis.requestDto.CaseWorkerRequestDto;
import com.rihis.requestDto.JwtRequestDto;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.ICaseWorkerSevice;

@RestController
@RequestMapping("/caseWorker")
@CrossOrigin("http://localhost:4200/")
public class CaseWorkerController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ICaseWorkerSevice workerSevice;
	
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
		System.out.println("api run..........");
		CaseWorker worker = workerSevice.createUser(dto);
		ApiResponse response = new ApiResponse(201, "created", worker);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
