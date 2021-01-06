package com.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.audit.domain.AuditPlanMaster;
import com.audit.domain.AuditProcedureMaster;
import com.audit.domain.User;

@Repository
public interface AuditPlanMasterRepository extends JpaRepository<AuditPlanMaster, Long>,JpaSpecificationExecutor<AuditPlanMaster> {

	Optional<AuditPlanMaster> findById(Long id);

	List<AuditPlanMaster> findAll();
	
	List<AuditPlanMaster> findAllByAssignTo(User user);
	
	Page<AuditPlanMaster> findAllByRemarksNot(Pageable pageable, String remarks);
	
	List<AuditPlanMaster> findAllByProcedureId(AuditProcedureMaster auditProcedureMaster);
}
