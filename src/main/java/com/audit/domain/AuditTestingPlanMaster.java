/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "audit_testing_plan_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditTestingPlanMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "risk_area_id")
	private Long riskAreaId;

	@Column(name = "expected_revert_date")
	private Instant expectedRevertDate;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;

	@OneToMany(mappedBy = "testingPlanId", cascade = CascadeType.ALL)
	private List<TestingPlanObservationMaster> testingPlanObservationMasterList;

	@JoinColumn(name = "audit_plan_id", referencedColumnName = "id",nullable = true)
	@ManyToOne
	private AuditPlanMaster auditPlanId;
	
	@Column(name = "testdescription")
	private String testDescription;

	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private DeptMaster deptId;
	
	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CostCentreMaster costCentreId;
	
	@Column(name = "rating")
	private Long rating;
	
	@Column(name = "test_result")
	private Long testResult;
	
	@Column(name="locked")
	private Boolean locked;

	@JoinColumn(name = "auditeeid", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User auditeeId;

	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@ManyToOne
	private User createdBy;

	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;

	@JoinColumn(name = "auditorid", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User auditorId;

	@NotNull
	@Column(name = "is_responded")
	private boolean isResponded;

	@Column(name = "actiontaken")
	private Integer actionTaken;

	public AuditTestingPlanMaster() {
	}

	public AuditTestingPlanMaster(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRiskAreaId() {
		return riskAreaId;
	}

	public void setRiskAreaId(Long riskAreaId) {
		this.riskAreaId = riskAreaId;
	}

	public Instant getExpectedRevertDate() {
		return expectedRevertDate;
	}

	public void setExpectedRevertDate(Instant expectedRevertDate) {
		this.expectedRevertDate = expectedRevertDate;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<TestingPlanObservationMaster> getTestingPlanObservationMasterList() {
		return testingPlanObservationMasterList;
	}

	public void setTestingPlanObservationMasterList(
			List<TestingPlanObservationMaster> testingPlanObservationMasterList) {
		this.testingPlanObservationMasterList = testingPlanObservationMasterList;
	}

	public AuditPlanMaster getAuditPlanId() {
		return auditPlanId;
	}

	public void setAuditPlanId(AuditPlanMaster auditPlanId) {
		this.auditPlanId = auditPlanId;
	}

	public User getAuditeeId() {
		return auditeeId;
	}

	public void setAuditeeId(User auditeeId) {
		this.auditeeId = auditeeId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public User getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(User auditorId) {
		this.auditorId = auditorId;
	}

	public DeptMaster getDeptId() {
		return deptId;
	}

	public void setDeptId(DeptMaster deptId) {
		this.deptId = deptId;
	}

	public CostCentreMaster getCostCentreId() {
		return costCentreId;
	}

	public void setCostCentreId(CostCentreMaster costCentreId) {
		this.costCentreId = costCentreId;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Long getTestResult() {
		return testResult;
	}

	public void setTestResult(Long testResult) {
		this.testResult = testResult;
	}

	public boolean isResponded() {
		return isResponded;
	}

	public void setResponded(boolean isResponded) {
		this.isResponded = isResponded;
	}

	public Integer getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(Integer actionTaken) {
		this.actionTaken = actionTaken;
	}
	
	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "com.audit.domain.AuditTestingPlanMaster[ id=" + id + " ]";
	}

}
