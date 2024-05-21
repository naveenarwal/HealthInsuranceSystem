package com.rihis.service;

import java.util.Map;

import com.rihis.requestDto.FilterRequestDto;

public interface IReportsService {

	public Map<String, Object> getFilterData(FilterRequestDto dto,Integer page,Integer size);
}
