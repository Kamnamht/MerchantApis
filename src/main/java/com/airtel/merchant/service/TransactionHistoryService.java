package com.airtel.merchant.service;

import org.springframework.stereotype.Service;

import com.airtel.merchant.api.response.MerTxnHistoryRes;
import com.airtel.merchant.bean.DistToRetTxnHistoryReq;

@Service
public interface TransactionHistoryService {
	MerTxnHistoryRes getAppTransHistory(DistToRetTxnHistoryReq distToRetTxnHistoryReq);

	MerTxnHistoryRes getPortalTransHistory(DistToRetTxnHistoryReq distToRetTxnHistoryReq);

	String getRefundAmount(String request);
}
