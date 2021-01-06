package com.audit.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "audit_plan_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditPlanMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "remarks", length = 255)
	private String remarks;

	@Column(name = "audit_plan_entity", length = 50)
	private String auditPlanEntity;

	@Column(name = "start_date")
	private Instant startDate;

	@Column(name = "end_date")
	private Instant endDate;
	@NotNull
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	@Column(name = "created_date")
	private Instant createdDate;
	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;

	@OneToMany(mappedBy = "planId", cascade = CascadeType.ALL)
	private List<AuditPlanObjectiveMap> auditPlanObjectiveMapCollection;
	@JoinColumn(name = "procedure_id", referencedColumnName = "id")
	@ManyToOne
	private AuditProcedureMaster procedureId;
	@JoinColumn(name = "assign_to", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User assignTo;
	@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User createdBy;
	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;
	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	@ManyToOne
	private DeptMaster deptId;

	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	@ManyToOne(cascade = CascadeType.ALL)
	private CostCentreMaster costCentreId;
	
	@JoinColumn(name = "location", referencedColumnName = "id")
	@ManyToOne
	private LocationMaster location;

	public AuditPlanMaster() {
	}

	public AuditPlanMaster(Long id) {
		this.id = id;
	}

	public AuditPlanMaster(Long id, boolean isActive) {
		this.id = id;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<AuditPlanObjectiveMap> getAuditPlanObjectiveMapCollection() {
		return auditPlanObjectiveMapCollection;
	}

	public void setAuditPlanObjectiveMapCollection(List<AuditPlanObjectiveMap> auditPlanObjectiveMapCollection) {
		this.auditPlanObjectiveMapCollection = auditPlanObjectiveMapCollection;
	}

	public String getAuditPlanEntity() {
		return auditPlanEntity;
	}

	public void setAuditPlanEntity(String auditPlanEntity) {
		this.auditPlanEntity = auditPlanEntity;
	}

	public AuditProcedureMaster getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(AuditProcedureMaster procedureId) {
		this.procedureId = procedureId;
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

	public User getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(User assignTo) {
		this.assignTo = assignTo;
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

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	


	public LocationMaster getLocation() {
		return location;
	}

	public void setLocation(LocationMaster location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AuditPlanMaster)) {
			return false;
		}
		AuditPlanMaster other = (AuditPlanMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.AuditPlanMaster[ id=" + id + " ]";
	}

}
