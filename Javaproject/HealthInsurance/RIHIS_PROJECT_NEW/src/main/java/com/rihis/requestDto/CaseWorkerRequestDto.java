package com.rihis.requestDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseWorkerRequestDto {
	
	private String caseWorkerName;
	
	private String email;
	
	private String mobileNumber;
	
	private String gender;
	
	//private String password;
	
	private String ssnNumber;
	
//	@Past(message = "Invalid Date !!!")
	private LocalDate dob;
}
