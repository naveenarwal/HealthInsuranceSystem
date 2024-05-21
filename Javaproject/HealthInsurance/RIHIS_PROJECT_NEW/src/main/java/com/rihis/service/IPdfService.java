package com.rihis.service;

import java.io.ByteArrayInputStream;

public interface IPdfService {

	public ByteArrayInputStream createPdf(Integer caseNumber);
}
