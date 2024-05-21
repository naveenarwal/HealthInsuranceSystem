package com.rihis.service;

import java.util.Map;
import java.util.Optional;

import com.rihis.model.CaseWorker;
import com.rihis.requestDto.CaseWorkerRequestDto;

public interface ICaseWorkerSevice {
	
	public CaseWorker createUser(CaseWorkerRequestDto requestDto);

	public Boolean generatePassword(String password,String email);

	public Boolean accountActivateAndDeactivate(String username);

	public Boolean deleteAccounts(String username);

	public  Map<String, Object> viewAccounts(Integer page, Integer size);
	
	public CaseWorker getCaseWorker(String email);
}
