/*
 *
 */
package com.airtel.merchant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airtel.merchant.model.DBAuditLog;

@Repository
public interface DBAuditLogRepository extends JpaRepository<DBAuditLog, Long> {

}
