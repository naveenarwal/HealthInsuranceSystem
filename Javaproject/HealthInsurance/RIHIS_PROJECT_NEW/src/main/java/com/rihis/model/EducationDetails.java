package com.rihis.model;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class EducationDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer educationId;
	
	private String highestDegree;
	
	private String universityName;
	
	private Year graduationYear;
}
