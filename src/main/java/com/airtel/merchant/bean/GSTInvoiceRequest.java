package com.airtel.merchant.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GSTInvoiceRequest {

	private String	deviceId;
	private String	ver;
	private String	appVersion;
	private String	feSessionId;
	private String	languageId;
	private String	channel;
	private String	mode;
	private String actorId;
	private String month;

	
}
