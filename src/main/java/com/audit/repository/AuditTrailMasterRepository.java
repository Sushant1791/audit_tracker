package com.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.AuditTrailMaster;

@Repository
public interface AuditTrailMasterRepository extends JpaRepository<AuditTrailMaster, Long>  {

}
