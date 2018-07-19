package com.airtel.merchant.model;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class VoltTxnLog {
	private String business_engine_session_id;
	private String Txn_Date_Time;
	private String Uc_Id;
	private String Fe_Id;
	private String Customer_Id;
	private String Service_Provider_Session_Id;
	private String Custom_Col_1;
	private String Custom_Col_19;
	private String Error_Message;
	private String Custom_Col_20;
	private String Custom_Col_13;
	private String REFUND_AMOUNT;
	private String ASSISTED_MOB_NO;

	public String getBUSINESS_ENGINE_SESSION_ID() {
		return business_engine_session_id;
	}

	public void setBUSINESS_ENGINE_SESSION_ID(String bUSINESS_ENGINE_SESSION_ID) {
		business_engine_session_id = bUSINESS_ENGINE_SESSION_ID;
	}

	public String getTXN_DATE_TIME() {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//	    return df.format(Txn_Date_Time);
		return Txn_Date_Time; 
	}

	public void setTXN_DATE_TIME(String tXN_DATE_TIME) {
		Txn_Date_Time = tXN_DATE_TIME;
	}

	public String getUC_ID() {
		return Uc_Id;
	}

	public void setUC_ID(String uC_ID) {
		Uc_Id = uC_ID;
	}

	public String getFE_ID() {
		return Fe_Id;
	}

	public void setFE_ID(String fE_ID) {
		Fe_Id = fE_ID;
	}

	public String getCUSTOMER_ID() {
		return Customer_Id;
	}

	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		Customer_Id = cUSTOMER_ID;
	}

	public String getSERVICE_PROVIDER_SESSION_ID() {
		return Service_Provider_Session_Id;
	}

	public void setSERVICE_PROVIDER_SESSION_ID(String sERVICE_PROVIDER_SESSION_ID) {
		Service_Provider_Session_Id = sERVICE_PROVIDER_SESSION_ID;
	}

	public String getCUSTOM_COL_1() {
		return Custom_Col_1;
	}

	public void setCUSTOM_COL_1(String cUSTOM_COL_1) {
		Custom_Col_1 = cUSTOM_COL_1;
	}

	public String getCUSTOM_COL_19() {
		return Custom_Col_19;
	}

	public void setCUSTOM_COL_19(String cUSTOM_COL_19) {
		Custom_Col_19 = cUSTOM_COL_19;
	}

	public String getERROR_MESSAGE() {
		return Error_Message;
	}

	public void setERROR_MESSAGE(String eRROR_MESSAGE) {
		Error_Message = eRROR_MESSAGE;
	}

	public String getCUSTOM_COL_20() {
		return Custom_Col_20;
	}

	public void setCUSTOM_COL_20(String cUSTOM_COL_20) {
		Custom_Col_20 = cUSTOM_COL_20;
	}

	public String getCUSTOM_COL_13() {
		return Custom_Col_13;
	}

	public void setCUSTOM_COL_13(String cUSTOM_COL_13) {
		Custom_Col_13 = cUSTOM_COL_13;
	}

	public String getREFUND_AMOUNT() {
		return REFUND_AMOUNT;
	}

	public void setREFUND_AMOUNT(String rEFUND_AMOUNT) {
		REFUND_AMOUNT = rEFUND_AMOUNT;
	}
	
	public String getASSISTED_MOB_NO() {
		return ASSISTED_MOB_NO;
	}

	public void setASSISTED_MOB_NO(String aSSISTED_MOB_NO) {
		ASSISTED_MOB_NO = aSSISTED_MOB_NO;
	}
}