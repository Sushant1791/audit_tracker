package com.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.AuditProcObjectiveMaster;
import com.audit.domain.AuditProcedureMaster;

@Repository
public interface AuditObjectiveMasterRepository   extends JpaRepository<AuditProcObjectiveMaster, Long> {

	Long deleteByProcedureId(AuditProcedureMaster auditProcedureMaster);
	
}
