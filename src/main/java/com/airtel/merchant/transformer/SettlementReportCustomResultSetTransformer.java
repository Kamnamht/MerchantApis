/*
 *
 */
package com.airtel.merchant.transformer;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import com.airtel.merchant.model.SettlementReport;

public class SettlementReportCustomResultSetTransformer implements ResultTransformer {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object transformTuple(Object[] objects, String[] strings) {

		final SettlementReport result = new SettlementReport();

		for (int i = 0; i < objects.length; i++) {
			setField(result, strings[i], objects[i]);
		}

		return result;
	}

	private void setField(SettlementReport result, String string, Object object) {

		if (string.equalsIgnoreCase("S.No")) {
			result.setS_NO((BigDecimal) object);
		} else if (string.equalsIgnoreCase("Merchant Id")) {
			result.setMERCHANT_ID(((String) object));
		} else if (string.equalsIgnoreCase("Merchant MSISDN")) {
			result.setMERCHANT_MSISDN(((String) object));
		} else if (string.equalsIgnoreCase("Merchant Name")) {
			result.setMERCHANT_NAME((String) object);
		} else if (string.equalsIgnoreCase("Merchant Account Number")) {
			result.setMERCHANT_ACCOUNT_NUMBER((String) object);
		} else if (string.equalsIgnoreCase("Merchant Settlement Type")) {
			result.setMERCHANT_SETTLEMENT_TYPE(((String) object));
		} else if (string.equalsIgnoreCase("TXN_DATE")) {
			result.setTXN_DATE(((String) object));
		} else if (string.equalsIgnoreCase("Bank Txn Ref No")) {
			result.setBANK_TXN_REF_NO(((String) object));
		} else if (string.equalsIgnoreCase("Partner Txn Ref No")) {
			result.setPARTNER_TXN_REF_NO(((String) object));
		} else if (string.equalsIgnoreCase("Customer MSISDN")) {
			result.setCUSTOMER_MSISDN(((String) object));
		} else if (string.equalsIgnoreCase("CUST_TYPE")) {
			result.setCUSTOMER_CATEGORY(((String) object));
		} else if (string.equalsIgnoreCase("Transaction Type")) {
			result.setTRANSACTION_TYPE(((String) object));
		} else if (string.equalsIgnoreCase("ORIG_AMNT")) {
			result.setORIG_AMNT(((String) object));
		} else if (string.equalsIgnoreCase("Commision(DR)")) {
			result.setCOMMISION_DR_(((String) object));
		} else if (string.equalsIgnoreCase("COMMISION_DR_")) {
			result.setCOMMISION_DR_(((String) object));
		} else if (string.equalsIgnoreCase("COMMISION(CR)")) {
			result.setCOMMISION_CR_(((String) object));
		} else if (string.equalsIgnoreCase("UTR Num")) {
			result.setUTR_NUM(((String) object));
		} else if (string.equalsIgnoreCase("Settlement Date")) {
			result.setSETTLEMENT_DATE(((String) object));
		} else if (string.equalsIgnoreCase("SC(DR)")) {
			result.setSC_DR_(((String) object));
		} else if (string.equalsIgnoreCase("SC(CR)")) {
			result.setSC_CR_(((String) object));
		} else if (string.equalsIgnoreCase("UGST(CR)")) {
			result.setUGST_CR_(((String) object));
		} else if (string.equalsIgnoreCase("UGST(DR)")) {
			result.setUGST_DR_(((String) object));
		} else if (string.equalsIgnoreCase("TDS(DR)")) {
			result.setTDS_DR_(((String) object));
		} else if (string.equalsIgnoreCase("TDS(CR)")) {
			result.setTDS_CR_(((String) object));
		} else if (string.equalsIgnoreCase("CGST(DR)")) {
			result.setCGST_DR_(((String) object));
		} else if (string.equalsIgnoreCase("CGST(CR)")) {
			result.setCGST_CR_(((String) object));
		} else if (string.equalsIgnoreCase("Till ID")) {
			result.setTILL_ID(((String) object));
		} else if (string.equalsIgnoreCase("IGST(DR)")) {
			result.setIGST_DR_(((String) object));
		} else if (string.equalsIgnoreCase("IGST(CR)")) {
			result.setIGST_CR_(((String) object));
		} else if (string.equalsIgnoreCase("Store Id")) {
			result.setSTORE_ID(((String) object));
		} else if (string.equalsIgnoreCase("SGST(CR)")) {
			result.setSGST_CR_(((String) object));
		} else if (string.equalsIgnoreCase("SGST(DR)")) {
			result.setSGST_DR_(((String) object));
		} else if (string.equalsIgnoreCase("Net Credit Amnt")) {
			result.setNET_CREDIT_AMNT(((String) object));
		} else {
			throw new RuntimeException("unknown field");
		}

	}

	@Override
	public List<SettlementReport> transformList(List list) {
		return list;
	}
}
