/*
 *
 */
package com.airtel.merchant.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.airtel.merchant.bean.GetSettlementRequestBean;
import com.airtel.merchant.dto.MerchantSettlementRes;
import com.airtel.merchant.dto.SettlementReportDTO;
import com.airtel.merchant.dto.SettlementReports;
import com.airtel.merchant.exceptions.MerchantNotFoundException;
import com.airtel.merchant.model.DBAuditLog;
import com.airtel.merchant.model.filter.LoggerModel;
import com.airtel.merchant.model.filter.LoggerModel.LoggerError;
import com.airtel.merchant.service.SettlementReportService;
import com.airtel.merchant.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@RestController
public class SettlementReportController {

	private static final Logger logger = LoggerFactory.getLogger(SettlementReportController.class);
	private @Autowired SettlementReportService settlementReportService;
	private @Autowired AppUtils appUtils;
	private @Autowired ObjectMapper objectMapper;

	@ApiOperation(value = "Displays Settlement Report within selected Time Frame", notes = "Use following values:<br><br>"
			+ "fromDate : from Date  - Mandatory <br>" + "toDate : to Date  -  Mandatory <br>")
	@RequestMapping(value = "/settlementreport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MerchantSettlementRes getSettlementReport(HttpServletRequest request, @RequestHeader("jwt_payload") String jwtModelString) {
		logger.info("REQUEST POST /settlementreport ");
		final LoggerModel loggerModel = new LoggerModel();
		final List<LoggerError> loggerErrorList = new ArrayList<LoggerError>();
		loggerModel.setErrors(loggerErrorList);
		loggerModel.setStartTime(System.currentTimeMillis());
		final MerchantSettlementRes response = new MerchantSettlementRes();
		final DBAuditLog dbAuditLog = (DBAuditLog) request.getAttribute("dbAuditLog");
		dbAuditLog.setAction("getSettlementReport");
		try {
			final String merchantId = appUtils.getMerchantIdFromJWTModel(jwtModelString);
			if (StringUtils.isEmpty(merchantId)) {
				throw new MerchantNotFoundException("No merchant found with given Id ");
			}
			loggerModel.setRequest(((String) request.getAttribute("requestData")).trim());
			loggerModel.setMerchantId(merchantId);
			dbAuditLog.setCUSTOMER_ID(merchantId);

			final String settlementReqBeanString = (String) request.getAttribute("requestData");
			final GetSettlementRequestBean settlementReqBean = objectMapper.readValue(settlementReqBeanString, GetSettlementRequestBean.class);

			final List<SettlementReportDTO> reports = settlementReportService.getSettlementReport(merchantId,
					settlementReqBean.getFromDate(), settlementReqBean.getToDate());
			logger.info("received total results " + reports.size());

			if (!CollectionUtils.isEmpty(reports)) {
				response.setSettlementRecords(new SettlementReports(reports));
				response.setErrorCode("0000");
				response.setMessageText("Success");
				response.setNoRecords(String.valueOf(reports.size()));
			} else {
				response.setErrorCode("79876");
				response.setMessageText("No Record Found");
			}
			loggerModel.setResponse(response);
		} catch (final MerchantNotFoundException e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			logger.error(e.getMessage());
			response.setCode("1");
			response.setMessageText(e.getMessage());
		} catch (final ParseException e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			logger.error(e.getMessage());
			response.setCode("1");
			response.setMessageText("Unparseable Data format");
		} catch (final Exception e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			logger.error(e.getMessage());
			response.setCode("1");
			response.setMessageText("Server problem.. Try Again Later");
		}	finally	{
			logger.warn(appUtils.convertLoggerModelToString(loggerModel));
			if (response.toString().length() >= 3991) {
				dbAuditLog.setRESPONSE_DETAIL(response.toString().substring(0, 3990));
			}	else		{
				dbAuditLog.setRESPONSE_DETAIL(response.toString());
			}
			dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
			dbAuditLog.setRESPONSE_CODE(response.getCode());
			dbAuditLog.setERRORCODE(response.getErrorCode());
			dbAuditLog.setRESPONSE_MESSAGE(response.getMessageText());
		}
		return response;
	}
}
