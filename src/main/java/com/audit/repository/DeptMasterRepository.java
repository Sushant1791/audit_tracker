package com.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.DeptMaster;

@Repository
public interface DeptMasterRepository  extends JpaRepository<DeptMaster, Long>  {

	List<DeptMaster> findAllByIsActive(Boolean isActive);

}
