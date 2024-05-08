package com.springboot.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import com.springboot.model.Customer;
import com.springboot.model.QuoteItem;



@Service
public class PdfGeneratorService {

	@Autowired
	private TemplateEngine templateEngine;
	
	

	@Value("${pdf.directory}")
	private String pdfDirectory;

	public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName,String password) {
		Context context = new Context();
		context.setVariables(data);

		String htmlContent = templateEngine.process(templateName, context);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
			ITextRenderer renderer = new ITextRenderer();
            // Create PDF encryption object
            PDFEncryption pdfEncryption = new PDFEncryption();
            pdfEncryption.setUserPassword(password.getBytes());

            // Set the encryption on the renderer
            renderer.setPDFEncryption(pdfEncryption);
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(fileOutputStream, false);
			renderer.finishPDF();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Object> generateData(Customer customer) {
		Map<String, Object> data = new HashMap<>();
		
		data.put("customer", customer);
		
		List<QuoteItem> quoteItems = new ArrayList<>();
        QuoteItem quoteItem1 = new QuoteItem();
        quoteItem1.setDescription("Test Quote Item 1");
        quoteItem1.setQuantity(1);
        quoteItem1.setUnitPrice(100.0);
        quoteItem1.setTotal(100.0);
        quoteItems.add(quoteItem1);

        QuoteItem quoteItem2 = new QuoteItem();
        quoteItem2.setDescription("Test Quote Item 2");
        quoteItem2.setQuantity(4);
        quoteItem2.setUnitPrice(500.0);
        quoteItem2.setTotal(2000.0);
        quoteItems.add(quoteItem2);

        QuoteItem quoteItem3 = new QuoteItem();
        quoteItem3.setDescription("Test Quote Item 3");
        quoteItem3.setQuantity(2);
        quoteItem3.setUnitPrice(200.0);
        quoteItem3.setTotal(400.0);
        quoteItems.add(quoteItem3);

        data.put("quoteItems", quoteItems);
		return data;
	}


}
