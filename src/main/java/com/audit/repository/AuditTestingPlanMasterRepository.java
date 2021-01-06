package com.audit.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.audit.domain.AuditTestingPlanMaster;
import com.audit.domain.DeptMaster;
import com.audit.domain.LocationMaster;
import com.audit.domain.TestingPlanObservationMaster;
import com.audit.domain.User;

@Repository
public interface AuditTestingPlanMasterRepository   extends JpaRepository<AuditTestingPlanMaster, Long>,JpaSpecificationExecutor<AuditTestingPlanMaster> {
	
	@Query("Select observation from TestingPlanObservationMaster observation "
			+ "INNER JOIN AuditTestingPlanMaster audit ON audit.id = observation.testingPlanId "
			+"INNER JOIN AuditPlanMaster plan ON audit.auditPlanId = plan.id "
			+ "where audit.deptId =:deptId and TO_CHAR(audit.expectedRevertDate,'MM') =:month and TO_CHAR(audit.expectedRevertDate,'YYYY') =:year and plan.location =:location")
	public List<TestingPlanObservationMaster> findAllByDepartmentAndMonthYear(@Param("deptId")DeptMaster deptId,@Param("month") String month,@Param("year") String year, @Param("location") LocationMaster location);
	
	public List<AuditTestingPlanMaster> findAllByAuditeeId(User user);
	
	@Query(value = "select * from audit_testing_plan_master where audit_plan_id =:planid", nativeQuery= true)
	public List<AuditTestingPlanMaster> findByAuditPlanId(@Param("planid")Long planid);
	
	public List<AuditTestingPlanMaster> findAllByAuditorId(User user);
	
	
	@Query(value =  " select" + 
					" *," + 
					" (EXTRACT(epoch from age(now()::::date,testing.expected_revert_date::::date)) / 86400)::::int AS overduedays " + 
					" from audit_testing_plan_master testing " + 
					" inner join audit_plan_master plan on plan.id = testing.audit_plan_id " + 
					" where testing.id != 0 " + 
					" and (LOWER(plan.audit_plan_entity) like CONCAT('%', LOWER(:name), '%')) " + 
					" GROUP BY testing.id,plan.id " + 
					" HAVING (EXTRACT(epoch from age(now()::::date,testing.expected_revert_date::::date)) / 86400)::::int > :duedays ", nativeQuery = true)
	public List<AuditTestingPlanMaster> findAllAuditeeResponseWithOutDate( @Param("name")String name,  @Param("duedays")Integer dueDays,Pageable p);
	
	public long countByRating(long rating);
	public long countByRatingAndAuditorId(long rating, User user);
	
	@Query(value="select count(*) from audit_testing_plan_master where is_responded =:responded", nativeQuery = true)
	public long countByResponded(@Param("responded") boolean responded);
	
	@Query(value="select count(*) from audit_testing_plan_master where is_responded =:responded  and auditorid=:auditorId", nativeQuery = true)
	public long countByRespondedByAuditorId(@Param("responded") boolean responded, @Param("auditorId") Long auditorId);
	
	@Query(value="select count(*) from audit_testing_plan_master where locked =:locked", nativeQuery = true)
	public long countByLocked(@Param("locked") boolean locked);
	
	@Query(value="select count(*) from audit_testing_plan_master where locked =:locked  and auditorid=:auditorId", nativeQuery = true)
	public long countByLockedByAuditorId(@Param("locked") boolean locked, @Param("auditorId") Long auditorId);
}
