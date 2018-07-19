/*
 *
 */
package com.airtel.merchant.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.airtel.merchant.model.VoltTxnLog;

@Transactional(value = "voltTransactionManager")
@Component("Transaction")
public class TransactionHistoryRepositoryImpl {

	@PersistenceContext(unitName = "voltEntityManagerFactory")
	private EntityManager entityManager;

	private static final Logger log = LoggerFactory.getLogger(TransactionHistoryRepositoryImpl.class);

	@SuppressWarnings("deprecation")
	public List<VoltTxnLog> executeNativeQuery(String query, String merchantId, String transactionStatus,
			String transactionId, String partnerTransId, String mobileNumber, String fromDate, String toDate,
			String lowerLimit, String upperLimit, String merchantAccountNo) {
		log.info("executing native sql query ");

		final long startTime = System.currentTimeMillis();

		@SuppressWarnings("unchecked")
		final List<VoltTxnLog> transactions = entityManager.unwrap(org.hibernate.Session.class).createNativeQuery(query)
				.setParameter("TRANSACTION_STATUS", StringUtils.isEmpty(transactionStatus) ? "1" : transactionStatus)
				.setParameter("MSISDN", StringUtils.isEmpty(merchantId) ? "1" : merchantId)

				.setParameter("TRANSACTION_ID", StringUtils.isEmpty(transactionId) ? "1" : transactionId)
				.setParameter("PARTNER_TRANSACTION_ID", StringUtils.isEmpty(partnerTransId) ? "1" : partnerTransId)
				.setParameter("MOBILE_NUMBER",
						StringUtils.isEmpty(mobileNumber) ? "1"
								: (mobileNumber.length() == 12 ? mobileNumber.substring(2) : mobileNumber))
				.setParameter("FROM_DATE", fromDate).setParameter("TO_DATE", toDate)
				.setParameter("LOWER_LIMIT", StringUtils.isEmpty(lowerLimit) ? "0" : lowerLimit)
				.setParameter("UPPER_LIMIT", StringUtils.isEmpty(upperLimit) ? "50" : upperLimit)
				.setParameter("ACCNO", merchantAccountNo.trim())
				.addScalar("business_engine_session_id", StandardBasicTypes.STRING)
				.addScalar("Uc_Id", StandardBasicTypes.STRING).addScalar("Custom_Col_19", StandardBasicTypes.STRING)
				.addScalar("Fe_Id", StandardBasicTypes.STRING).addScalar("Customer_Id", StandardBasicTypes.STRING)
				.addScalar("Error_Message", StandardBasicTypes.STRING)
				.addScalar("Custom_Col_1", StandardBasicTypes.STRING)
				.addScalar("Custom_Col_20", StandardBasicTypes.STRING)
				.addScalar("Service_Provider_Session_Id", StandardBasicTypes.STRING)
				.addScalar("Custom_Col_13", StandardBasicTypes.STRING)
				.addScalar("REFUND_AMOUNT", StandardBasicTypes.STRING)
				.addScalar("Txn_Date_Time", StandardBasicTypes.STRING)
				.addScalar("ASSISTED_MOB_NO", StandardBasicTypes.STRING)
				.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(VoltTxnLog.class)).list();

		final long time = System.currentTimeMillis() - startTime;
		log.info("Total time taken to execute query " + time);

		return transactions;
	}

	public String executeNativeQuery(String txnRefNo) {
		try {
			final String query = "Select sum(custom_col_1)as REFUNDED_AMOUNT from VOLT_TXN_LOG where SERVICE_PROVIDER_SESSION_ID='"
					+ txnRefNo + "' and CUSTOM_COL_20 in ('REFUNDED','REFUND_SUCCESS')";
			final Object obj = entityManager.createNativeQuery(query).getSingleResult();
			if (obj == null) {
				return "0";
			}
			log.info("Returned from DB" + obj.toString());
			return obj.toString();
		} catch (final Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
