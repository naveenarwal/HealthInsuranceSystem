package com.rihis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorrespondenceTrigger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coTriggerId;
	
	private Integer caseNumber;
	
	@Lob
	private byte[] coPdf;
	
	private String noticeStatus = "PENDING";
}
