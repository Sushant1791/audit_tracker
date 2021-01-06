package com.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.LocationMaster;

@Repository
public interface LocationRepository extends JpaRepository<LocationMaster, Long> {

	List<LocationMaster> findAllByIsActive(Boolean isActive);
	
}
