/*
 * 
 */
package com.airtel.merchant.api.response;

import java.util.ArrayList;
import java.util.List;

import com.airtel.merchant.dto.MerchantTransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerTxnHistoryRes {

	protected String noRecords;
	protected MerTxnHistoryRes.TxnRecords txnRecords;
	protected String errorCode;
	protected String messageText;
	protected String code;

	public String getNoRecords() {
		return noRecords;
	}

	public void setNoRecords(String value) {
		noRecords = value;
	}

	public MerTxnHistoryRes.TxnRecords getTxnRecords() {
		return txnRecords;
	}

	public void setTxnRecords(MerTxnHistoryRes.TxnRecords value) {
		txnRecords = value;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String value) {
		errorCode = value;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String value) {
		messageText = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String value) {
		code = value;
	}

	public static class TxnRecords {
		protected List<MerchantTransactionDTO> txnRecord;

		public List<MerchantTransactionDTO> getTxnRecord() {
			if (txnRecord == null) {
				txnRecord = new ArrayList<MerchantTransactionDTO>();
			}
			return txnRecord;
		}

		@Override
		public String toString() {
			return "TxnRecords [txnRecord=" + txnRecord + "]";
		}
	}
}