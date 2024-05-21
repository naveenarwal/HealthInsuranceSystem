package com.rihis.requestDto;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import com.rihis.model.EducationDetails;
import com.rihis.model.IncomeDetails;
import com.rihis.model.Kid;
import com.rihis.model.Plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenRequestDto {

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
	
	private Plan plan;
	
	private List<Kid> kids;
	
	private IncomeDetails income;
	
	private EducationDetails educationDetails;
	
}
