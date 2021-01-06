package com.audit.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

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
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "dept_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeptMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "dept_name", nullable = false, length = 50)
	private String deptName;

	@NotNull
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;

	@OneToMany(mappedBy = "deptId")
	private Collection<AuditProcedureMaster> auditProcedureMasterCollection;

	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@ManyToOne
	private User createdBy;
	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;

	public DeptMaster() {
	}

	public DeptMaster(Long id) {
		this.id = id;
	}

	public DeptMaster(Long id, String deptName, boolean isActive) {
		this.id = id;
		this.deptName = deptName;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	@XmlTransient
	public Collection<AuditProcedureMaster> getAuditProcedureMasterCollection() {
		return auditProcedureMasterCollection;
	}

	public void setAuditProcedureMasterCollection(Collection<AuditProcedureMaster> auditProcedureMasterCollection) {
		this.auditProcedureMasterCollection = auditProcedureMasterCollection;
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
		if (!(object instanceof DeptMaster)) {
			return false;
		}
		DeptMaster other = (DeptMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.DeptMaster[ id=" + id + " ]";
	}

}
