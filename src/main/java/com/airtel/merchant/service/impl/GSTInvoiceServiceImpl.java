package com.airtel.merchant.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.airtel.merchant.bean.GSTInvoiceRequest;
import com.airtel.merchant.dto.MerchantGSTDTO;
import com.airtel.merchant.service.GSTInvoiceService;
import com.airtel.merchant.utils.APIClient;
import com.airtel.merchant.utils.ESignPdfService;
import com.itextpdf.text.pdf.codec.Base64;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Component
public class GSTInvoiceServiceImpl  implements GSTInvoiceService{

	@Autowired
	ESignPdfService signPdfService;
	
	
	private static Logger logger = LoggerFactory.getLogger(GSTInvoiceServiceImpl.class);

	
	@Override
	public byte[] getDigitalSignedPDF(GSTInvoiceRequest request) throws Exception{
		MerchantGSTDTO merchantGSTDTO= new MerchantGSTDTO();
		merchantGSTDTO.setActor_id(request.getActorId());
		merchantGSTDTO.setMonth(request.getMonth());
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("actor", merchantGSTDTO);
		String ftlRequest = getFtlRequest("GST.ftl", input);
		
		String responseString = APIClient.getSoapResponse(ftlRequest);
		String finalByte = getEncodedDataFromXml(responseString);
		
		decodeBase64(finalByte,"GSTInvoice"+request.getActorId());
		logger.info("GST invoice Generated for Actor "+request.getActorId());
		signPdfService.generateESignPdf("GSTInvoice"+request.getActorId(), "signedGSTInvoice"+request.getActorId());
		logger.info("Digital Signed GST invoice Generated for Actor "+request.getActorId());
		File signedInvoice = new File("signedGSTInvoice"+request.getActorId());
		File unsignedInvoice = new File("GSTInvoice"+request.getActorId());
		Path path = Paths.get("signedGSTInvoice"+request.getActorId());
		byte[] data = Files.readAllBytes(path);
		unsignedInvoice.deleteOnExit();
		signedInvoice.deleteOnExit();
		return data;
	}
	
	public String getFtlRequest(String fileName, Map<String, Object> mapObj)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration();

		// Where do we load the templates from:
		// cfg.setClassForTemplateLoading(MainTest.class, "/");

		cfg.setDirectoryForTemplateLoading(new File("templates"));

		// Some other recommended settings:
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Template template = cfg.getTemplate(fileName);

		Writer out = new StringWriter();
		template.process(mapObj, out);
		String transformedTemplate = out.toString();
		out.flush();
		return transformedTemplate;
	}
	
	public String getEncodedDataFromXml(String response) throws Exception {
		try {
			InputStream weatherAsStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fac.newDocumentBuilder();
			org.w3c.dom.Document weatherDoc = builder.parse(weatherAsStream);

			return weatherDoc.getElementsByTagName("reportBytes").item(0).getTextContent();
		} catch (Exception e) {
			//log.info("Exception occured while fetching encoded data" + e);
			throw new Exception ("Exception" +e);
		}
		

	}
	
	public void decodeBase64(String encodedData, String destFile) throws Exception {
		FileOutputStream fos = new FileOutputStream(destFile);
		fos.write(Base64.decode(encodedData));
		fos.close();
	}

}
