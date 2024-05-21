package com.rihis.responseDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseWorkerResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer caseWorkerId;
	
	private String caseWorkerName;
	
	private String email;
	
	private String mobileNumber;
	
	private String gender;
	
	private String ssnNumber;
	
	private LocalDate dob;
	
	private LocalDate createdDate;
	
	private boolean isDeleted ;
	
	private boolean isActive;

	private List<String> roles;
}
