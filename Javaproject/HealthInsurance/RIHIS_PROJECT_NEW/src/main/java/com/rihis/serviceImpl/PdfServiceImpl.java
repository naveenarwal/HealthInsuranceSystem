package com.rihis.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.rihis.exception.ResourceNotFoundException;
import com.rihis.model.Citizen;
import com.rihis.model.CorrespondenceTrigger;
import com.rihis.model.EligibilityDetermination;
import com.rihis.repository.CitizenRepository;
import com.rihis.repository.CorrespondenceRepository;
import com.rihis.repository.EligibilityDeterminationRepository;
import com.rihis.service.IPdfService;

@Service
public class PdfServiceImpl implements IPdfService{

	
	@Autowired
	private CorrespondenceRepository correspondenceRepository;
	
	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private EligibilityDeterminationRepository determinationRepository;
	
	private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

	public ByteArrayInputStream createPdf(Integer caseNumber) {
		
		logger.info("Create PDF Started : ");
		
		Citizen citizen = citizenRepository.findByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		
		EligibilityDetermination determination = determinationRepository.findByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		
		String title="RIHIS (Rhode Island Health Insurance System)";
		String content="We provide fully integrated health and insurance plans for Rhode Island state citizens";
		String message=" Hello "+citizen.getCitizenName()+" The details of your Application Id : "+citizen.getApplicationId()+" is mentioned as below\n\n"
				+"Application Status : "+ citizen.getStatus()+"\nPlan Name : "+citizen.getPlan().getPlanName();
		
		if(determination.getBenefitAmount()!=null)
			message = message+"\nBenefit Amount : "+determination.getBenefitAmount();
		
		if(determination.getDenialReason()!=null)
			message = message+"\nDenial Reason : "+determination.getDenialReason();
				
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Document document = new Document();
		
		PdfWriter.getInstance(document, out);
		
		document.open();
		
		HeaderFooter footer=new HeaderFooter(false,new Phrase("This is a computer generated slip, no seal or signature required"));
		 footer.setAlignment(Element.ALIGN_CENTER);
		 footer.setBorderWidthBottom(0);
		 document.setFooter(footer);
		document.open();
		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,25);
		
		Paragraph titlePara = new Paragraph(title,titleFont);
		
		titlePara.setAlignment(Element.ALIGN_CENTER);
		
		document.add(titlePara);
		
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA,18);
		Paragraph paragraph = new Paragraph(content);
		
		document.add(paragraph);
		
		Font messageFont=FontFactory.getFont(FontFactory.COURIER,12);		
		Paragraph messagePara=new Paragraph(message,messageFont);		
		messagePara.setAlignment(Element.ALIGN_LEFT);
		
		document.add(messagePara);
		
		document.close();
		
		CorrespondenceTrigger correspondence = correspondenceRepository.findByCaseNumber(caseNumber).orElseThrow(()-> new ResourceNotFoundException("DATA_NOT_FOUND"));
		correspondence.setCoPdf(out.toByteArray());
		correspondenceRepository.save(correspondence);
		
		return new ByteArrayInputStream(out.toByteArray());
	}

}
