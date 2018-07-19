package com.airtel.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSettlementRes  {

	protected String			noRecords = "0";
	protected SettlementReports	settlementRecords;
	private String errorCode = "000";
	private String messageText = "SUCCESS";
	private String code = "0";

}
