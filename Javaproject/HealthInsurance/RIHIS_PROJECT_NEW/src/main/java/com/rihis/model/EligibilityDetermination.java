package com.rihis.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class EligibilityDetermination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer edTraceId;
	
	private Integer caseNumber;
	
	private String holderName;
	
	private String email;
	
	private String ssnNumber;
	
	private String planName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String status;
	
	private Double benefitAmount;
	
	private String denialReason;
	
}
