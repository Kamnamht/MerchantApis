/*
 *
 */
package com.airtel.merchant.dto;

import com.airtel.merchant.model.VoltTxnLog;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchantTransactionDTO {

	private String ampTxnId;
	private String dateTime;
	private String txnType;
	private String name;
	private String mobNumber;
	private String description;
	private String amount;
	private String txnStatus;
	private String paymentRefNo;
	private String ucIDForRefund;
	private String customerIDForRefund;
	private String amountRefundedForRefund;
	private String assistedMobileNo;
	private String feId;

	public MerchantTransactionDTO(VoltTxnLog record) {
		ampTxnId = record.getBUSINESS_ENGINE_SESSION_ID();
		dateTime = record.getTXN_DATE_TIME();
		txnType = record.getUC_ID();
		name = null;
		mobNumber = record.getCUSTOM_COL_19();
		description = record.getERROR_MESSAGE();
		amount = record.getCUSTOM_COL_1();
		txnStatus = record.getCUSTOM_COL_20();
		paymentRefNo = record.getSERVICE_PROVIDER_SESSION_ID();
		ucIDForRefund = record.getCUSTOM_COL_13();
		customerIDForRefund = record.getCUSTOMER_ID();
		amountRefundedForRefund = record.getREFUND_AMOUNT();
		assistedMobileNo = record.getASSISTED_MOB_NO();
		feId = record.getFE_ID();
	}
}
