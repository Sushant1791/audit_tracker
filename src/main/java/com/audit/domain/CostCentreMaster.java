package com.audit.domain;

import java.io.Serializable;
import java.time.Instant;

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
@Table(name = "cost_centre_master", catalog = "AuditTracker", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CostCentreMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "cost_centre_name", nullable = false, length = 50)
	private String costCentreName;

	@NotNull
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;
	
	@JoinColumn(name = "last_modified_by", referencedColumnName = "id")
	@ManyToOne
	private User lastModifiedBy;
	@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User createdBy;
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	@ManyToOne
	private User ownerId;
	@JoinColumn(name = "reporting_id", referencedColumnName = "id")
	@ManyToOne
	private User reportingId;

	public CostCentreMaster() {
	}

	public CostCentreMaster(Long id) {
		this.id = id;
	}

	public CostCentreMaster(Long id, String costCentreName, Boolean isActive) {
		this.id = id;
		this.costCentreName = costCentreName;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCostCentreName() {
		return costCentreName;
	}

	public void setCostCentreName(String costCentreName) {
		this.costCentreName = costCentreName;
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

	

	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(User ownerId) {
		this.ownerId = ownerId;
	}

	public User getReportingId() {
		return reportingId;
	}

	public void setReportingId(User reportingId) {
		this.reportingId = reportingId;
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
		if (!(object instanceof CostCentreMaster)) {
			return false;
		}
		CostCentreMaster other = (CostCentreMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.CostCentreMaster[ id=" + id + " ]";
	}

}
