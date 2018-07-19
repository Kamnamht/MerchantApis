/*
 *
 */
package com.airtel.merchant.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.airtel.merchant.api.response.MerTxnHistoryRes;
import com.airtel.merchant.api.response.MerTxnHistoryRes.TxnRecords;
import com.airtel.merchant.bean.DistToRetTxnHistoryReq;
import com.airtel.merchant.dto.MerchantTransactionDTO;
import com.airtel.merchant.helper.TransactionReportHelper;
import com.airtel.merchant.model.VoltTxnLog;
import com.airtel.merchant.repository.impl.TransactionHistoryRepositoryImpl;
import com.airtel.merchant.service.TransactionHistoryService;
import com.airtel.merchant.utils.AppUtils;

@Component
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

	private @Autowired TransactionHistoryRepositoryImpl repo;
	private @Autowired TransactionReportHelper transactionReportHelper;

	@Override
	public MerTxnHistoryRes getAppTransHistory(DistToRetTxnHistoryReq distToRetTxnHistoryReq) {
		final String query = AppUtils.readFile("/transactionHistoryQuery.txt");
		final MerTxnHistoryRes response = new MerTxnHistoryRes();

		if (StringUtils.isBlank(distToRetTxnHistoryReq.getTxnType())) {
			response.setCode("1");
			response.setErrorCode("1234567");
			response.setMessageText("Invalid txnType.");
			return response;
		}
		try {
			distToRetTxnHistoryReq.setFromDate(new SimpleDateFormat("dd-MM-yyyy")
					.format(new SimpleDateFormat("dd-MMM-yy").parse(distToRetTxnHistoryReq.getFromDate())));
			distToRetTxnHistoryReq.setToDate(new SimpleDateFormat("dd-MM-yyyy")
					.format(new SimpleDateFormat("dd-MMM-yy").parse(distToRetTxnHistoryReq.getToDate())));// new
		} catch (final ParseException e) {
			logger.error("Merchant app transaction date parsing exception " + e);
		}

		// get Account no for given MSISDN from CBS
		final String merchantAccountNo = transactionReportHelper
				.getMerchantAccountNoFromCBS(distToRetTxnHistoryReq.getMerchantId());

		final List<VoltTxnLog> transactions = repo.executeNativeQuery(query, distToRetTxnHistoryReq.getMerchantId(),
				distToRetTxnHistoryReq.getTxnStatus(), distToRetTxnHistoryReq.getAmpTxnId(),
				distToRetTxnHistoryReq.getPaymentRefNo(), distToRetTxnHistoryReq.getMobNumber(),
				distToRetTxnHistoryReq.getFromDate(), distToRetTxnHistoryReq.getToDate(),
				distToRetTxnHistoryReq.getMinRecords(), distToRetTxnHistoryReq.getMaxRecords(), merchantAccountNo);
		logger.info("Query returned " + transactions.size() + " results.");
		if (transactions != null && transactions.size() > 0) {
			final TxnRecords txnRecords = new TxnRecords();

			for (int i = 0; i < transactions.size(); i++) {
				final VoltTxnLog voLog = transactions.get(i);

				if ("SUCCESS".equalsIgnoreCase(voLog.getCUSTOM_COL_20())) {
					final MerchantTransactionDTO merchantTransactionDTO=	new MerchantTransactionDTO(voLog);
					txnRecords.getTxnRecord().add(merchantTransactionDTO);
					merchantTransactionDTO.setDateTime(merchantTransactionDTO.getDateTime()+".0");

				}

			}
			response.setTxnRecords(txnRecords);
			response.setErrorCode("0000");
			response.setMessageText("Success");
			response.setNoRecords(String.valueOf(transactions.size()));
			response.setCode("0");
		} else {
			response.setCode("0");
			response.setErrorCode("79876");
			response.setMessageText("No Record Found");
		}
		return response;
	}

	@Override
	public MerTxnHistoryRes getPortalTransHistory(DistToRetTxnHistoryReq distToRetTxnHistoryReq) {
		final String query = AppUtils.readFile("/transactionHistoryQuery.txt");
		final MerTxnHistoryRes response = new MerTxnHistoryRes();

		try {
			distToRetTxnHistoryReq.setFromDate(new SimpleDateFormat("dd-MM-yyyy")
					.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(distToRetTxnHistoryReq.getFromDate())));// new
			// SimpleDateFormat("dd-MMM-yy")
			distToRetTxnHistoryReq.setToDate(new SimpleDateFormat("dd-MM-yyyy")
					.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(distToRetTxnHistoryReq.getToDate())));// new
		} catch (final ParseException e) {
			logger.error("Merchant portal transaction date parsing exception " + e);
		}

		// get Account no for given MSISDN from CBS
		final String merchantAccountNo = transactionReportHelper
				.getMerchantAccountNoFromCBS(distToRetTxnHistoryReq.getMerchantId());
		logger.info("account no for merchant "+distToRetTxnHistoryReq.getMerchantId()+" "+merchantAccountNo);
		final List<VoltTxnLog> transactions = repo.executeNativeQuery(query, distToRetTxnHistoryReq.getMerchantId(),
				distToRetTxnHistoryReq.getTxnStatus(), distToRetTxnHistoryReq.getAmpTxnId(),
				distToRetTxnHistoryReq.getPaymentRefNo(), distToRetTxnHistoryReq.getMobNumber(),
				distToRetTxnHistoryReq.getFromDate(), distToRetTxnHistoryReq.getToDate(),
				distToRetTxnHistoryReq.getMinRecords(), distToRetTxnHistoryReq.getMaxRecords(), merchantAccountNo);
		if (transactions != null && transactions.size() > 0) {
			final TxnRecords txnRecords = new TxnRecords();
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");// hh:MM aaa
			final SimpleDateFormat sdfFromJasper = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (int i = 0; i < transactions.size(); i++) {
				final MerchantTransactionDTO transaction = new MerchantTransactionDTO(transactions.get(i));
				Date dateinBIFormat;
				try {
					dateinBIFormat = sdfFromJasper.parse(transaction.getDateTime());
					transaction.setDateTime(sdf.format(dateinBIFormat));
				} catch (final ParseException e) {
					logger.error("Merchant portal transaction result date parsing exception " + e);
				}

				if ("USSD".equals(transaction.getFeId())) {
					transaction.setMobNumber(transaction.getCustomerIDForRefund().substring(2));
				}
				txnRecords.getTxnRecord().add(transaction);
			}
			response.setTxnRecords(txnRecords);
			response.setErrorCode("0000");
			response.setMessageText("Success");
			response.setNoRecords(String.valueOf(transactions.size()));
			response.setCode("0");
		} else {
			response.setCode("0");
			response.setErrorCode("79876");
			response.setMessageText("No Record Found");
		}
		return response;
	}

	@Override
	public String getRefundAmount(String paymentRefNo) {
		final String amount = repo.executeNativeQuery(paymentRefNo);
		return amount;
	}

}
