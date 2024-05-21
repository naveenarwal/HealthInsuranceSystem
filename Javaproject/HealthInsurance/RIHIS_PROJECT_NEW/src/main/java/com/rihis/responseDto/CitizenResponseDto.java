package com.rihis.responseDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenResponseDto {

	private Integer citizenId;
	
	private String citizenName;
	
	private String email;
	
	private String mobileNumber;
	
	private String gender;
	
	private LocalDate dob;
	
	private LocalDate createdDate;
	
	private LocalDate lastUpdatedDate;
	
	private String ssnNumber;
	
	private Integer applicationId;
	
	private String status;
	
	private Integer caseNumber;
}
