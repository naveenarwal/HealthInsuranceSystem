package com.rihis.model;

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
public class Kid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kidId;
	
	private String kidName;
	
	private Integer age;
	
	private String ssnNumber;
}
