/*
 * 
 */
package com.airtel.merchant.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.airtel.merchant.dto.SettlementReportDTO;
import com.airtel.merchant.model.SettlementReport;
import com.airtel.merchant.repository.impl.SettlementReportRepositoryImpl;
import com.airtel.merchant.service.SettlementReportService;
import com.airtel.merchant.utils.AppUtils;

@Component
public class SettlementReportServiceImpl implements SettlementReportService {

	private static final Logger logger = LoggerFactory.getLogger(SettlementReportServiceImpl.class);
	private @Autowired SettlementReportRepositoryImpl settlementRepository;

	@Qualifier("customModelMapper")
	private @Autowired ModelMapper modelMapper;

	@Override
	public List<SettlementReportDTO> getSettlementReport(String merchantId, String fromDate, String toDate) throws ParseException {
		logger.info("inside getSettlementReport with merchantId " + merchantId + " fromDate " + fromDate + " toDate "
				+ toDate);
		fromDate = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyyMMddHHmmss").parse(fromDate));
		toDate = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyyMMddHHmmss").parse(toDate));
		final String query = AppUtils.readFile("/settlementReportQuery.txt");
		final List<SettlementReport> settlementReports = settlementRepository.executeNativeQuery(query, merchantId, fromDate,
				toDate);
		logger.info("Query returned " + settlementReports.size() + " results.");
		return mapToDTO(settlementReports);
	}

	private List<SettlementReportDTO> mapToDTO(List<SettlementReport> settlementReports) {
		logger.info("converting SettlementReport to SettlementReportDTO");
		final List<SettlementReportDTO> settlementReportDTOList = new ArrayList<SettlementReportDTO>();
		for (final SettlementReport settlementReport : settlementReports) {
			settlementReportDTOList.add(modelMapper.map(settlementReport, SettlementReportDTO.class));
		}
		return settlementReportDTOList;
	}

}
