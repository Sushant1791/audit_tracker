/**
 * 
 */
package com.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audit.domain.CostCentreMaster;

/**
 * @author Jignesh
 *
 */

@Repository
public interface CostCentreRepository   extends JpaRepository<CostCentreMaster, Long> {
	List<CostCentreMaster> findAllByIsActive(Boolean isActive);
}
