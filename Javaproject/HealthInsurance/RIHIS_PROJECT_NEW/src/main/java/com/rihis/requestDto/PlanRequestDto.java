package com.rihis.requestDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequestDto {

	private String planName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String planCategory;
}
