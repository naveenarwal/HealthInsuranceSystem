package com.rihis.responseDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponseDto {

	private Integer planId;
	
	private String planName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String planCategory;
	
	private Boolean isDeleted  = false;
	
	private Boolean isActive;
}
