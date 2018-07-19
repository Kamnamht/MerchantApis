/*
 *
 */
package com.airtel.merchant.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.airtel.merchant.api.response.APIResponse;
import com.airtel.merchant.model.filter.LoggerModel;
import com.airtel.merchant.model.jwt.JWTModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppUtils {

	private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);
	private @Autowired ObjectMapper objectMapper;
	private @Value("${merchantAPIServiceId}") String merchantAPIServiceId;

	public static String readFile(String fileName) {
		InputStream fis = null;
		try {
			fis = AppUtils.class.getResourceAsStream(fileName);
			final byte[] byteArr = new byte[fis.available()];
			fis.read(byteArr);
			final String data = new String(byteArr);
			return data;
		} catch (final IOException e) {
			return "";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static <T> APIResponse<T> getSuccessfulResponse(T t) {
		return (new APIResponse<T>(1, t));
	}

	public static <T> APIResponse<T> getErrorResponse(T errorObject) {
		return new APIResponse<T>(0, errorObject);
	}

	public static <T> APIResponse<T> getErrorResponse(int statusCode, T errorObject) {
		return new APIResponse<T>(statusCode, errorObject);
	}

	public String getMerchantIdFromJWTModel(String jwtModelString) {
		String merchantId = null;
		try {
			final JWTModel jwtModel = objectMapper.readValue(jwtModelString, JWTModel.class);
			if (!StringUtils.isEmpty(jwtModel.getParentMsisdn())) {
				if (jwtModel.getParentMsisdn().length() >= 12) {
					merchantId = jwtModel.getParentMsisdn().substring(2, jwtModel.getParentMsisdn().length());
					logger.info("merchantId " + merchantId);
				}
			} else {
				if (jwtModel.getMobileno().length() >= 12) {
					merchantId = jwtModel.getMobileno().substring(2, jwtModel.getMobileno().length());
					logger.info("merchantId " + merchantId);
				}
			}
		} catch (final IOException e) {
			logger.error("Exception occurred while getting merchantId from jwt " + e.getMessage());
		}
		return merchantId;
	}

	public String convertLoggerModelToString(LoggerModel loggerModel) {
		// ServiceId | partnerId | Request | Response |
		// errorCode,errorMsg-errorCode,errorMsg |
		// methodName, methodTime- methodName, methodTime | TotalTime

		final StringBuilder builder = new StringBuilder();
		int errorCode = 0;
		builder.append(merchantAPIServiceId + " | ");

		if (!CollectionUtils.isEmpty(loggerModel.getErrors())) {
			errorCode = 1;
		}
		// builder.append(errorCode + " | ");
		builder.append(loggerModel.getMerchantId() + " | ");
		builder.append(loggerModel.getRequest() + " | ");
		try {
			System.out.println(objectMapper.writeValueAsString(loggerModel.getResponse().toString()));
			builder.append(objectMapper.writeValueAsString(loggerModel.getResponse()) + " | ");
		} catch (final JsonProcessingException e) {
			builder.append(null + " | ");
		}

		if (errorCode == 1) {
			if (!CollectionUtils.isEmpty(loggerModel.getErrors())) {
				for (int i = 0; i < loggerModel.getErrors().size(); i++) {
					if (i != 0) {
						builder.append("-");
					}
					builder.append(loggerModel.getErrors().get(i).getErrorCode() + ","
							+ loggerModel.getErrors().get(i).getErrorDescription());
				}
			}
		} else {
			builder.append("000");
		}

		builder.append(" | ");
		if (!CollectionUtils.isEmpty(loggerModel.getMethods())) {
			for (int i = 0; i < loggerModel.getMethods().size(); i++) {
				if (i != 0) {
					builder.append(" - ");
				}
				builder.append("Method Name " + loggerModel.getMethods().get(i).getMethodName() + ", Method Response "
						+ ", Total Time taken " + loggerModel.getMethods().get(i).getMethodTime());
			}
		}

		final long endTime = System.currentTimeMillis();
		final long time = (endTime - loggerModel.getStartTime());

		builder.append(" | ");
		builder.append(time);
		return builder.toString();
	}
}
