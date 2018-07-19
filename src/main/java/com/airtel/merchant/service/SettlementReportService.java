/*
 * 
 */
package com.airtel.merchant.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.airtel.merchant.dto.SettlementReportDTO;

@Service
public interface SettlementReportService {
	List<SettlementReportDTO> getSettlementReport(String merchantId, String fromDate, String toDate) throws ParseException;
}
