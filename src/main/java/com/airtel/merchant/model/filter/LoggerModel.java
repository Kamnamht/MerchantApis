/*
 *
 */
package com.airtel.merchant.model.filter;

import java.util.List;

import lombok.Data;

@Data
public class LoggerModel {

	private long startTime;
	private String merchantId;
	private String request;
	private Object response;
	private List<LoggerError> errors;
	private List<MethodStats> methods;

	public static class LoggerError {

		private String errorCode;
		private String errorDescription;

		public LoggerError() {

		}

		public LoggerError(String errorCode, String errorDescription) {
			this.errorCode = errorCode;
			this.errorDescription = errorDescription;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorDescription() {
			return errorDescription;
		}

		public void setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
		}

		@Override
		public String toString() {
			return " errorCode " + errorCode + " errorDescription " + errorDescription + " ";
		}
	}

	public static class MethodStats {
		private String methodName;
		private long methodTime;

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public long getMethodTime() {
			return methodTime;
		}

		public void setMethodTime(long methodTime) {
			this.methodTime = methodTime;
		}

		public MethodStats(String methodName, long methodTime) {
			this.methodName = methodName;
			this.methodTime = methodTime;
		}

	}

}
