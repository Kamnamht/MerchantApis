/*
 *
 */
package com.airtel.merchant.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.airtel.merchant.model.SettlementReport;
import com.airtel.merchant.transformer.SettlementReportCustomResultSetTransformer;

@Transactional(value = "cbsTransactionManager")
@Component("Settlement")
public class SettlementReportRepositoryImpl {

	private static final Logger logger = LoggerFactory.getLogger(SettlementReportRepositoryImpl.class);

	@PersistenceContext(unitName = "cbsEntityManagerFactory")
	private EntityManager entityManager;

	public List<SettlementReport> executeNativeQuery(String query, String merchantId, String fromDate, String toDate) {
		logger.info("executing native sql query ");

		final long startTime = System.currentTimeMillis();

		@SuppressWarnings("unchecked")
		final
		List<SettlementReport> settlementReportList = entityManager.unwrap(org.hibernate.Session.class)
				.createNativeQuery(query.trim()).setParameter("MERCHANT_ID", merchantId)
				.setParameter("FROM_DATE", fromDate).setParameter("TO_DATE", toDate)
				.addScalar("S.No", StandardBasicTypes.BIG_DECIMAL).addScalar("Merchant Id", StandardBasicTypes.STRING)
				.addScalar("Merchant MSISDN", StandardBasicTypes.STRING)
				.addScalar("Merchant Name", StandardBasicTypes.STRING)
				.addScalar("Merchant Account Number", StandardBasicTypes.STRING)
				.addScalar("Merchant Settlement Type", StandardBasicTypes.STRING)
				.addScalar("TXN_DATE", StandardBasicTypes.STRING).addScalar("Bank Txn Ref No", StandardBasicTypes.STRING)
				.addScalar("Partner Txn Ref No", StandardBasicTypes.STRING)
				.addScalar("Customer MSISDN", StandardBasicTypes.STRING)
				.addScalar("CUST_TYPE", StandardBasicTypes.STRING)
				.addScalar("Transaction Type", StandardBasicTypes.STRING)
				.addScalar("Orig_Amnt", StandardBasicTypes.STRING).addScalar("Commision(DR)", StandardBasicTypes.STRING)
				.addScalar("Commision(CR)", StandardBasicTypes.STRING).addScalar("SC(DR)", StandardBasicTypes.STRING)
				.addScalar("SC(CR)", StandardBasicTypes.STRING).addScalar("UGST(DR)", StandardBasicTypes.STRING)
				.addScalar("UGST(CR)", StandardBasicTypes.STRING).addScalar("TDS(DR)", StandardBasicTypes.STRING)
				.addScalar("TDS(CR)", StandardBasicTypes.STRING).addScalar("CGST(DR)", StandardBasicTypes.STRING)
				.addScalar("CGST(CR)", StandardBasicTypes.STRING).addScalar("SGST(DR)", StandardBasicTypes.STRING)
				.addScalar("SGST(CR)", StandardBasicTypes.STRING)
				.addScalar("Net Credit Amnt", StandardBasicTypes.STRING)
				.addScalar("Store Id", StandardBasicTypes.STRING).addScalar("Till ID", StandardBasicTypes.STRING)
				.addScalar("UTR Num", StandardBasicTypes.STRING).addScalar("Settlement Date", StandardBasicTypes.STRING)
				.setResultTransformer(new SettlementReportCustomResultSetTransformer()).setFetchSize(100).list();

		final long time = System.currentTimeMillis() - startTime;
		logger.info("Total time taken to execute query " + time);

		return settlementReportList;
	}
}
