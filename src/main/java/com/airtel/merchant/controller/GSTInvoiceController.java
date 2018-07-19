/*
 *
 */
package com.airtel.merchant.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.airtel.merchant.bean.GSTInvoiceRequest;
import com.airtel.merchant.exceptions.MerchantNotFoundException;
import com.airtel.merchant.model.DBAuditLog;
import com.airtel.merchant.model.filter.LoggerModel;
import com.airtel.merchant.model.filter.LoggerModel.LoggerError;
import com.airtel.merchant.service.GSTInvoiceService;
import com.airtel.merchant.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GSTInvoiceController {

	private static final Logger log = LoggerFactory.getLogger(GSTInvoiceController.class);

	private @Autowired GSTInvoiceService invoiceService;
	private @Autowired AppUtils appUtils;
	private @Autowired ObjectMapper objectMapper;

	@PostMapping(value = "/gstInvoices", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] gstInvoice(HttpServletRequest request, 
			@RequestHeader("jwt_payload") String jwtModelString) throws Exception {
		String responseCode = "1";
		String errorCode = "";
		String responseMessageText = "";
		final LoggerModel loggerModel = new LoggerModel();
		final List<LoggerError> loggerErrorList = new ArrayList<LoggerError>();
		loggerModel.setErrors(loggerErrorList);
		loggerModel.setStartTime(System.currentTimeMillis());
		final DBAuditLog dbAuditLog = (DBAuditLog) request.getAttribute("dbAuditLog");
		dbAuditLog.setAction("gstInvoice");
		try {
			final String merchantId = appUtils.getMerchantIdFromJWTModel(jwtModelString);
			log.info("merchantId from JWT token " + merchantId);
			if (StringUtils.isEmpty(merchantId)) {
				throw new MerchantNotFoundException("No merchant found with given Id ");
			}
			loggerModel.setMerchantId(merchantId);
			dbAuditLog.setCUSTOMER_ID(merchantId);

			final String gstInvoiceRequestString = (String) request.getAttribute("requestData");
			loggerModel.setRequest(gstInvoiceRequestString.trim());
			final GSTInvoiceRequest gstInvoiceRequest = objectMapper.readValue(gstInvoiceRequestString,
					GSTInvoiceRequest.class);

			final byte[] gstInvoice = invoiceService.getDigitalSignedPDF(gstInvoiceRequest);
			responseCode = "0";
			responseMessageText = "success";
			errorCode = "0000";
			loggerModel.setResponse(responseMessageText);
			return gstInvoice;
		} catch (final MerchantNotFoundException e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			responseMessageText = e.getMessage();
			return null;
		} catch (final Exception e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			responseMessageText = e.getMessage();
			return null;
		} finally {
			log.warn(appUtils.convertLoggerModelToString(loggerModel));
			dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
			dbAuditLog.setRESPONSE_CODE(responseCode);
			dbAuditLog.setERRORCODE(errorCode);
			dbAuditLog.setRESPONSE_MESSAGE(responseMessageText);
		}
	}

}
