/*
 *
 */
package com.airtel.merchant.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Transactional(value = "cbsTransactionManager")
@Component("CbsMobileAcctXref")
public class CbsMobileAcctXrefRepositoryImpl {

	@PersistenceContext(unitName = "cbsEntityManagerFactory")
	private EntityManager entityManager;

	@Value(value = "${cbs.account.find.query}")
	private String query;

	public String findByCOD_CUST_NATL_ID(String COD_CUST_NATL_ID) {
		final List<String> resultList = entityManager.unwrap(org.hibernate.Session.class)
		.createNativeQuery(query.trim()).setParameter("COD_CUST_NATL_ID", COD_CUST_NATL_ID).getResultList();
		if(!CollectionUtils.isEmpty(resultList))	{
			final String accNo = resultList.get(0);
			return accNo;
		}
		return "";
	}

}
