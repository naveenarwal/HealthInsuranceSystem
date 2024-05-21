package com.rihis.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyErrorResponse {

	private String date;
	
	private String status;
	
	private Integer Code;
	
	private String message;

}
