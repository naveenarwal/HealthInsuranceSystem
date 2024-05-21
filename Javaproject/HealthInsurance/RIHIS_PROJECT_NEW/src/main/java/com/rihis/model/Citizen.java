package com.rihis.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@ToString
public class Citizen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@ManyToOne
	@JoinTable(name = "citizen_plan", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "plan_id"))
	private Plan plan;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "citizen_kids", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "kid_id"))
	private List<Kid> kids;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "citizen_income", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "income_id"))
	private IncomeDetails income;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "citizen_educationDetails", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "education_id"))
	private EducationDetails educationDetails;
	
}
