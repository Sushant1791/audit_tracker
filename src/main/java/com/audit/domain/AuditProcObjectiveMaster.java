package com.audit.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "audit_proc_objective_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditProcObjectiveMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "objective_name", nullable = false, length = 50)
	private String objectiveName;
	@Column(name = "objective_description", length = 255)
	private String objectiveDescription;
	@NotNull
	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;
	@JoinColumn(name = "procedure_id", referencedColumnName = "id")
	@ManyToOne( cascade= CascadeType.ALL)
	private AuditProcedureMaster procedureId;
	@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User createdBy;
	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;

	public AuditProcObjectiveMaster() {
	}

	public AuditProcObjectiveMaster(Long id) {
		this.id = id;
	}

	public AuditProcObjectiveMaster(Long id, String objectiveName, boolean isActive) {
		this.id = id;
		this.objectiveName = objectiveName;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public String getObjectiveDescription() {
		return objectiveDescription;
	}

	public void setObjectiveDescription(String objectiveDescription) {
		this.objectiveDescription = objectiveDescription;
	}

	public boolean getIsActive() {
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

	public AuditProcedureMaster getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(AuditProcedureMaster procedureId) {
		this.procedureId = procedureId;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AuditProcObjectiveMaster)) {
			return false;
		}
		AuditProcObjectiveMaster other = (AuditProcObjectiveMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.AuditProcObjectiveMaster[ id=" + id + " ]";
	}

}
