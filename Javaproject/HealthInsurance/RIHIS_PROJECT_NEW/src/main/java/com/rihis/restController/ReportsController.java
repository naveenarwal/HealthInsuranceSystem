package com.rihis.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.requestDto.FilterRequestDto;
import com.rihis.responseDto.ApiResponse;
import com.rihis.service.IReportsService;

@RestController
@RequestMapping("/api/v1/rihis/reports")
@CrossOrigin("http://localhost:4200/")
public class ReportsController {

	@Autowired
	private IReportsService reportsService;
	
	@PostMapping("/getFilterReports")
	public ResponseEntity<ApiResponse> getFilterReports(@RequestBody FilterRequestDto dto,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
		System.out.println(dto);
		Map<String, Object> filterData = reportsService.getFilterData(dto, page, size);
		ApiResponse response = new ApiResponse(200, "success", filterData);
		return ResponseEntity.ok(response);
	}
}
