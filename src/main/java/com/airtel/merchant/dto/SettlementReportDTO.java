/*
 *
 */
package com.airtel.merchant.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementReportDTO {

	private BigDecimal S_NO;
	private String merchantId; ;
	private String merchantMSISDN;
	private String merchantName;
	private String merchantAccountNumber;
	private String merchantSettlementType;
	private String transactionDate;
	private String transactionId;
	private String partnerTransactionId;
	private String customerMobileNumber;
	private String customerCategory;
	private String transactionType;
	private String orignalAmount;
	private String commisionDr;
	private String commisionCr;
	private String cgstDr;
	private String cgstCr;
	private String sgstDr;
	private String sgstCr;
	private String igstDr;
	private String igstCr;
	private String utgstDr;
	private String utgstCr;
	private String tdsDr;
	private String tdsCr;
	private String scCr;
	private String netCreditAmount;
	private String storeId;
	private String tillId;
	private String utr;
	private String settlementDate;
}
