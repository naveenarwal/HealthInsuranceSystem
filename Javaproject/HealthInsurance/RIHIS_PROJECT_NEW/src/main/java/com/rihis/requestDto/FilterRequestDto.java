package com.rihis.requestDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequestDto {
	
	private Integer planId;
	
	private String status;
	
	private String gender;
	
	private LocalDate startDate;
	
	private LocalDate endDate;

}
