package com.dollop.app.payload;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {

	private Integer id;
	private String name;
	private String contact;
	private String address; 

	
	
}
