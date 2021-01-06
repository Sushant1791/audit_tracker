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
@Table(name = "audit_procedure_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditProcedureMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	@NotNull
	@Column(name = "procedure_title", nullable = false, length = 50)
	private String procedureTitle;
	@Column(name = "procedure_description", length = 255)
	private String procedureDescription;
	@NotNull
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "created_date")
	private Instant createdDate;
	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;
	
	@OneToMany(mappedBy = "procedureId", cascade= CascadeType.ALL)
	private List<AuditProcObjectiveMaster> auditProcObjectiveMaster;
	@JoinColumn(name = "dept_id", referencedColumnName = "id")
	@ManyToOne
	private DeptMaster deptId;
	@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User createdBy;
	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;
	
	@JoinColumn(name = "cc_id", referencedColumnName = "id")
	@ManyToOne
	private CostCentreMaster costCentreId;
	


	public AuditProcedureMaster() {
	}

	public AuditProcedureMaster(Long id) {
		this.id = id;
	}

	public AuditProcedureMaster(Long id, String procedureTitle, Boolean isActive) {
		this.id = id;
		this.procedureTitle = procedureTitle;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcedureTitle() {
		return procedureTitle;
	}

	public void setProcedureTitle(String procedureTitle) {
		this.procedureTitle = procedureTitle;
	}

	public String getProcedureDescription() {
		return procedureDescription;
	}

	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}

	public Boolean getIsActive() {
		return isActive;
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


	public List<AuditProcObjectiveMaster> getAuditProcObjectiveMasterCollection() {
		return auditProcObjectiveMaster;
	}

	public void setAuditProcObjectiveMasterCollection(
			List<AuditProcObjectiveMaster> auditProcObjectiveMasterCollection) {
		this.auditProcObjectiveMaster = auditProcObjectiveMasterCollection;
	}

	public DeptMaster getDeptId() {
		return deptId;
	}

	public void setDeptId(DeptMaster deptId) {
		this.deptId = deptId;
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

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	

	public List<AuditProcObjectiveMaster> getAuditProcObjectiveMaster() {
		return auditProcObjectiveMaster;
	}

	public void setAuditProcObjectiveMaster(List<AuditProcObjectiveMaster> auditProcObjectiveMaster) {
		this.auditProcObjectiveMaster = auditProcObjectiveMaster;
	}

	public CostCentreMaster getCostCentreId() {
		return costCentreId;
	}

	public void setCostCentreId(CostCentreMaster costCentreId) {
		this.costCentreId = costCentreId;
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
		if (!(object instanceof AuditProcedureMaster)) {
			return false;
		}
		AuditProcedureMaster other = (AuditProcedureMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.AuditProcedureMaster[ id=" + id + " ]";
	}

}
