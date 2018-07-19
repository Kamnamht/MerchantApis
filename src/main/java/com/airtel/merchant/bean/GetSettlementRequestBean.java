package com.airtel.merchant.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetSettlementRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;

	public GetSettlementRequestBean(String fromDateString, String toDateString) {
		super();
		this.fromDate = fromDateString;
		this.toDate = toDateString;
	}

	public GetSettlementRequestBean(String merchantId, String fromDateString, String toDateString) {
		super();
		this.fromDate = fromDateString;
		this.toDate = toDateString;
	}

	public GetSettlementRequestBean() {
		super();
	}
}
