package com.rihis.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class CaseWorker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer caseWorkerId;
	
	private String caseWorkerName;
	
	private String email;
	
	private String password;
	
	private String mobileNumber;
	
	private String gender;
	
	private String ssnNumber;
	
	private LocalDate dob;
	
	private LocalDate createdDate;
	
	private LocalDate lastUpdatedDate;
	
	private boolean isDeleted = false;
	
	private boolean isActive;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable
	private List<String> roles;
	
}

