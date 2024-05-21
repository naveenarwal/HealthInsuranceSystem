package com.rihis.service;

import java.util.List;
import java.util.Map;

import com.rihis.model.CorrespondenceTrigger;

public interface ICorrespondenceService {
	
	public CorrespondenceTrigger generateCorrespondence(Integer caseNumber);

	public Map<String, Object> getCorrensponence(Integer page,Integer size);
	
	public Map<String, Object> getApprovedAndPendingCorrensponence(Integer page,Integer size, String status);
	
	public boolean sendNoticeToApplicant(Integer caseNumber);
}
