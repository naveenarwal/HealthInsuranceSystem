package com.rihis.restController;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihis.service.IPdfService;

@RestController
@RequestMapping("/api/v1/rihis/Pdf")
@CrossOrigin("http://localhost:4200/")
public class PdfGenerateController {

	@Autowired
	private IPdfService pdfService;
	
	
	@GetMapping("/generatePdf")
	public ResponseEntity<InputStreamResource> generatePdf(@RequestParam("caseNumber") Integer caseNumber){
		ByteArrayInputStream createPdf = pdfService.createPdf(caseNumber);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;file=rihis.pdf");
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(createPdf));
	}
	
}
