/*
 *
 */
package com.airtel.merchant.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DBAUDITLOG", schema = "AMCARE")
public class DBAuditLog implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(sequenceName = "DBAUDITLOG_SEQ", name = "seq_generator", allocationSize = 1, schema = "AMCARE")
	@Column(name = "AUDIT_LOG_ID")
	private Long AUDIT_LOG_ID;
	private Timestamp RESPONSE_TIME;
	private String FESESSION_ID;
	private String action;
	private String partner;
	private Timestamp REQUEST_TIME;
	private String CUSTOMER_ID;
	private String REQUEST_IP;
	private String REQUEST_DETAIL;
	private String RESPONSE_DETAIL;
	private String RESPONSE_CODE;
	private String RESPONSE_MESSAGE;
	private String ERRORCODE;
	private String REFERENCE1;
	private String REFERENCE2;
	private String REFERENCE3;
	private String REFERENCE4;
	private String REFERENCE5;

}
