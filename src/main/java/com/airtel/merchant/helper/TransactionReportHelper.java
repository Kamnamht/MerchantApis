/*
 *
 */
package com.airtel.merchant.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.airtel.merchant.repository.impl.CbsMobileAcctXrefRepositoryImpl;

@Component
public class TransactionReportHelper {

	private @Autowired CbsMobileAcctXrefRepositoryImpl cbsMobileAcctXrefRepository;

	public String getMerchantAccountNoFromCBS(String custNatlId) {
		return cbsMobileAcctXrefRepository.findByCOD_CUST_NATL_ID(custNatlId);
	}
}
