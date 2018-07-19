package com.airtel.merchant.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettlementReport implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private BigDecimal S_NO;
	private String MERCHANT_ID;
	private String MERCHANT_MSISDN;
	private String MERCHANT_NAME;
	private String MERCHANT_ACCOUNT_NUMBER;
	private String MERCHANT_SETTLEMENT_TYPE;
	private String TXN_DATE;
	private String BANK_TXN_REF_NO;
	private String PARTNER_TXN_REF_NO;
	private String CUSTOMER_MSISDN;
	private String CUSTOMER_CATEGORY;
	private String TRANSACTION_TYPE;
	private String ORIG_AMNT;
	private String COMMISION_DR_;
	private String COMMISION_CR_;
	private String UTR_NUM;
	private String SETTLEMENT_DATE;
	private String SC_DR_;
	private String SC_CR_;
	private String UGST_CR_;
	private String UGST_DR_;
	private String TDS_DR_;
	private String TDS_CR_;
	private String CGST_DR_;
	private String CGST_CR_;
	private String TILL_ID;
	private String IGST_DR_;
	private String IGST_CR_;
	private String STORE_ID;
	private String SGST_CR_;
	private String SGST_DR_;
	private String NET_CREDIT_AMNT;
}
