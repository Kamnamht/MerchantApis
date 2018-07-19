package com.airtel.merchant.api.response;

public class ErrorResponse {

	private String code;

	private String message;

	private String stacktrace;

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public ErrorResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ErrorResponse(String code, String message, String stacktrace) {
		this(code,message);
		this.stacktrace = stacktrace;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

