package com.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.AuditProcedureMaster;

@Repository
public interface AuditObjectiveRepository  extends JpaRepository<AuditProcedureMaster, Long> {
	List<AuditProcedureMaster> findByProcedureTitleOrProcedureDescription(String procedureTitle,String procedureDescription);
}
