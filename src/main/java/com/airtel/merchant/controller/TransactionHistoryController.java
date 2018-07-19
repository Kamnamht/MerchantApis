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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.airtel.merchant.api.response.MerTxnHistoryRes;
import com.airtel.merchant.api.response.RefundAmountRes;
import com.airtel.merchant.bean.DistToRetTxnHistoryReq;
import com.airtel.merchant.bean.RefundAmountReq;
import com.airtel.merchant.exceptions.MerchantNotFoundException;
import com.airtel.merchant.model.DBAuditLog;
import com.airtel.merchant.model.filter.LoggerModel;
import com.airtel.merchant.model.filter.LoggerModel.LoggerError;
import com.airtel.merchant.service.TransactionHistoryService;
import com.airtel.merchant.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TransactionHistoryController {

	private static final Logger log = LoggerFactory.getLogger(TransactionHistoryController.class);

	private @Autowired TransactionHistoryService transactionHistoryService;
	private @Autowired AppUtils appUtils;
	private @Autowired ObjectMapper objectMapper;

	@RequestMapping(value = "/merchant/app", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MerTxnHistoryRes getAppTransHistory(HttpServletRequest request,
			@RequestHeader("jwt_payload") String jwtModelString) {
		log.info("REQUEST POST /merchant/app ");
		final LoggerModel loggerModel = new LoggerModel();
		final List<LoggerError> loggerErrorList = new ArrayList<LoggerError>();
		loggerModel.setErrors(loggerErrorList);
		loggerModel.setStartTime(System.currentTimeMillis());
		MerTxnHistoryRes res = new MerTxnHistoryRes();
		final DBAuditLog dbAuditLog = (DBAuditLog) request.getAttribute("dbAuditLog");
		dbAuditLog.setAction("getAppTransHistory");
		try {
			final String merchantId = appUtils.getMerchantIdFromJWTModel(jwtModelString);
			log.info("merchantId from JWT token " + merchantId);
			if (StringUtils.isEmpty(merchantId)) {
				throw new MerchantNotFoundException("No merchant found with given Id ");
			}
			loggerModel.setRequest(((String) request.getAttribute("requestData")).trim());
			loggerModel.setMerchantId(merchantId);
			dbAuditLog.setCUSTOMER_ID(merchantId);

			final String distToRetTxnHistoryReqString = (String) request.getAttribute("requestData");

			final DistToRetTxnHistoryReq distToRetTxnHistoryReq = objectMapper.readValue(distToRetTxnHistoryReqString,
					DistToRetTxnHistoryReq.class);

			distToRetTxnHistoryReq.setMerchantId(merchantId);
			log.info("invoking getAppTransHistory method " + distToRetTxnHistoryReq);
			res = transactionHistoryService.getAppTransHistory(distToRetTxnHistoryReq);
			loggerModel.setResponse(res);
		} catch (final MerchantNotFoundException e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			res.setCode("1");
			res.setMessageText(e.getMessage());
		} catch (final Exception e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			res.setCode("1");
			res.setMessageText("Server problem.. Try Again Later");
		} finally {
			log.warn(appUtils.convertLoggerModelToString(loggerModel));
			if (res.toString().length() >= 3991) {
				dbAuditLog.setRESPONSE_DETAIL(res.toString().substring(0, 3990));
			} else {
				dbAuditLog.setRESPONSE_DETAIL(res.toString());
			}
			dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
			dbAuditLog.setRESPONSE_CODE(res.getCode());
			dbAuditLog.setERRORCODE(res.getErrorCode());
			dbAuditLog.setRESPONSE_MESSAGE(res.getMessageText());
		}
		return res;
	}

	@RequestMapping(value = "/merchant/portal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public MerTxnHistoryRes getPortalTransHistory(HttpServletRequest request,
			@RequestHeader("jwt_payload") String jwtModelString) {
		log.info("REQUEST POST /merchant/portal ");
		final LoggerModel loggerModel = new LoggerModel();
		final List<LoggerError> loggerErrorList = new ArrayList<LoggerError>();
		loggerModel.setErrors(loggerErrorList);
		loggerModel.setStartTime(System.currentTimeMillis());
		MerTxnHistoryRes res = new MerTxnHistoryRes();
		final DBAuditLog dbAuditLog = (DBAuditLog) request.getAttribute("dbAuditLog");
		dbAuditLog.setAction("getPortalTransHistory");
		try {
			final String merchantId = appUtils.getMerchantIdFromJWTModel(jwtModelString);
			log.info("merchantId from JWT token " + merchantId);
			if (StringUtils.isEmpty(merchantId)) {
				throw new MerchantNotFoundException("No merchant found with given Id ");
			}
			loggerModel.setRequest((String) request.getAttribute("requestData"));
			loggerModel.setMerchantId(merchantId);
			dbAuditLog.setCUSTOMER_ID(merchantId);

			final String distToRetTxnHistoryReqString = (String) request.getAttribute("requestData");

			final DistToRetTxnHistoryReq distToRetTxnHistoryReq = objectMapper.readValue(distToRetTxnHistoryReqString,
					DistToRetTxnHistoryReq.class);

			distToRetTxnHistoryReq.setMerchantId(merchantId);

			log.info("invoking getAppTransHistory method " + distToRetTxnHistoryReq);
			res = transactionHistoryService.getPortalTransHistory(distToRetTxnHistoryReq);
			loggerModel.setResponse(res);
		} catch (final MerchantNotFoundException e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			res.setCode("1");
			res.setMessageText(e.getMessage());
		} catch (final Exception e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			res.setCode("1");
			res.setMessageText("Server problem.. Try Again Later");
		} finally {
			log.warn(appUtils.convertLoggerModelToString(loggerModel));
			if (res.toString().length() >= 3991) {
				dbAuditLog.setRESPONSE_DETAIL(res.toString().substring(0, 3990));
			} else {
				dbAuditLog.setRESPONSE_DETAIL(res.toString());
			}
			dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
			dbAuditLog.setRESPONSE_CODE(res.getCode());
			dbAuditLog.setERRORCODE(res.getErrorCode());
			dbAuditLog.setRESPONSE_MESSAGE(res.getMessageText());
		}
		return res;
	}

	@RequestMapping(value = "/merchant/portal/refund", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RefundAmountRes getRefundAmount(HttpServletRequest request) {
		final RefundAmountRes res = new RefundAmountRes();
		final DBAuditLog dbAuditLog = (DBAuditLog) request.getAttribute("dbAuditLog");
		dbAuditLog.setAction("getRefundAmount");
		final LoggerModel loggerModel = new LoggerModel();
		final List<LoggerError> loggerErrorList = new ArrayList<LoggerError>();
		loggerModel.setErrors(loggerErrorList);
		loggerModel.setStartTime(System.currentTimeMillis());
		try {
			final String refundAmountReqString = (String) request.getAttribute("requestData");
			loggerModel.setRequest((String) request.getAttribute("requestData"));
			final RefundAmountReq incomingRequest = objectMapper.readValue(refundAmountReqString, RefundAmountReq.class);

			if (StringUtils.isEmpty(incomingRequest.getPaymentRefNo())) {
				res.setCode("1");
				res.setMessageText("Invalid Payment Reference No.");
				loggerModel.setResponse(res);
				log.error("Invalid payment reference no");

				if (res.toString().length() >= 3991) {
					dbAuditLog.setRESPONSE_DETAIL(res.toString().substring(0, 3990));
				} else {
					dbAuditLog.setRESPONSE_DETAIL(res.toString());
				}
				dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
				dbAuditLog.setRESPONSE_CODE(res.getCode());
				dbAuditLog.setERRORCODE(null);
				dbAuditLog.setRESPONSE_MESSAGE(res.getMessageText());
				return res;
			}
			log.info("invoking getRefundAmount method " + incomingRequest);
			final String amount = transactionHistoryService.getRefundAmount(incomingRequest.getPaymentRefNo());
			if (amount != null) {
				res.setRefundedAmount(amount);
				res.setCode("0");
				res.setMessageText("Success");
			} else {
				res.setCode("1");
				res.setMessageText("Failed");
			}
			loggerModel.setResponse(res);
		} catch (final Exception e) {
			loggerModel.getErrors().add(new LoggerModel.LoggerError("1", e.getMessage()));
			log.error(e.getMessage());
			res.setCode("1");
			res.setMessageText("Server problem.. Try Again Later");
		} finally {
			log.warn(appUtils.convertLoggerModelToString(loggerModel));
			if (res.toString().length() >= 3991) {
				dbAuditLog.setRESPONSE_DETAIL(res.toString().substring(0, 3990));
			} else {
				dbAuditLog.setRESPONSE_DETAIL(res.toString());
			}
			dbAuditLog.setRESPONSE_TIME(new Timestamp(System.currentTimeMillis()));
			dbAuditLog.setRESPONSE_CODE(res.getCode());
			dbAuditLog.setERRORCODE(null);
			dbAuditLog.setRESPONSE_MESSAGE(res.getMessageText());
		}
		return res;
	}
}
