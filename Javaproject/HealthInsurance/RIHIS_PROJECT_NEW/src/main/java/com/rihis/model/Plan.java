package com.rihis.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer planId;
	
	private String planName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String planCategory;
	
	private LocalDate createdDate;
	
	private LocalDate lastUpdatedDate;
	
	private Boolean isDeleted  = false;
	
	private Boolean isActive;	
	
}
