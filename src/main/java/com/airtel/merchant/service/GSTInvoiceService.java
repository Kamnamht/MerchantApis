package com.airtel.merchant.service;

import org.springframework.stereotype.Service;

import com.airtel.merchant.bean.GSTInvoiceRequest;

@Service
public interface GSTInvoiceService {

	public byte[] getDigitalSignedPDF(GSTInvoiceRequest request) throws Exception;

}
