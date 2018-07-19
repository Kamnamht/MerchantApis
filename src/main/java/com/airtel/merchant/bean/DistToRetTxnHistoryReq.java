package com.airtel.merchant.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistToRetTxnHistoryReq {
	protected String actorId;
	protected String feSessionId;
	protected String fromDate;
	protected String toDate;
	protected String mpin;
	protected String mode;
	private String actorType;
	private String txnType;
	private String txnStatus;
	private String minRecords;
	private String maxRecords;
	protected String mobNumber;
	protected String ampTxnId;
	protected String paymentRefNo;
	private String merchantId;
}
