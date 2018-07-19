/*
 *
 */
package com.airtel.merchant.request.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.airtel.merchant.model.DBAuditLog;
import com.airtel.merchant.repository.DBAuditLogRepository;
import com.airtel.merchant.utils.AppConstant;

@javax.transaction.Transactional
@Component
@Order(1)
public class AuditLogFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuditLogFilter.class);
	private @Autowired DBAuditLogRepository dbAuditLogRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("inside doFilterInternal of" + this.getClass().getName());
		DBAuditLog dbAuditLog = new DBAuditLog();
		dbAuditLog.setREQUEST_TIME(new Timestamp(System.currentTimeMillis()));
		dbAuditLog.setPartner(AppConstant.AUDIT_LOG_PARTNER);
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		dbAuditLog.setREQUEST_IP(ipAddress);

		final String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))
				.replace("\n", "");
		logger.info("requestData = " + requestData);
		dbAuditLog.setREQUEST_DETAIL(requestData);

		final String fesessionId = request.getHeader(AppConstant.AUDIT_LOG_FESESSIONID_HEADER);
		dbAuditLog.setFESESSION_ID(fesessionId);
		logger.info("fesessionId in filter " + fesessionId);

		final MultipleReadHttpServletRequest multipleReadHttpServletRequest = new MultipleReadHttpServletRequest(
				request);
		multipleReadHttpServletRequest.setAttribute("dbAuditLog", dbAuditLog);
		multipleReadHttpServletRequest.setAttribute("requestData", requestData);

		if (response.getCharacterEncoding() == null) {
			response.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
		}

		final HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(response);
		logger.info("forwarding request to controller");
		filterChain.doFilter(multipleReadHttpServletRequest, responseCopier);
		logger.info("control received at filter again");

		dbAuditLog = (DBAuditLog) multipleReadHttpServletRequest.getAttribute("dbAuditLog");
		logger.info("dbAuditLog received from request attribute " + dbAuditLog);

		try {
			// final BigDecimal AUDIT_LOG_ID = dbAuditLogRepository.getNextSeq();
			// dbAuditLog.setAUDIT_LOG_ID(AUDIT_LOG_ID);

			logger.debug("saving dbAuditLog to repository");
			dbAuditLogRepository.save(dbAuditLog);
			logger.info("dbAuditLog saved successfully");
		} catch (final Exception e) {
			logger.error(e.getMessage());
		}

		final byte[] copy = responseCopier.getCopy();
		try {
			responseCopier.flushBuffer();
		} finally {

		}
	}
}
