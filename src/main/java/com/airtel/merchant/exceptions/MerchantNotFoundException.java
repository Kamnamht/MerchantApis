package com.airtel.merchant.exceptions;

public class MerchantNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MerchantNotFoundException() {
	}

	public MerchantNotFoundException(String message) {
		super(message);
	}
}
