package com.airtel.merchant.api.response;

public class APIResponse<T>{

	private int statusCode;
	private T result;
	public APIResponse(int statusCode, T result) {
		super();
		this.statusCode = statusCode;
		this.result = result;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public T getResult() {
		return result;
	}
}
