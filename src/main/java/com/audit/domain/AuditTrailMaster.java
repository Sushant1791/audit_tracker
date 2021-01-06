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

@Entity
@Table(name = "audit_trail_master")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditTrailMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@JoinColumn(name = "audit_by", referencedColumnName = "id")
	@ManyToOne
	private User auditBy;

	@Column(name = "audit_date")
	private Instant auditDate;

	@Column(name = "audit_event")
	private String auditEvent;

	@Column(name = "audit_type")
	private String auditType;

	@Column(name = "description")
	private String description;

	public AuditTrailMaster() {

	}

	public User getAuditBy() {
		return auditBy;
	}

	public Instant getAuditDate() {
		return auditDate;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	public String getAuditType() {
		return auditType;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public void setAuditBy(User auditBy) {
		this.auditBy = auditBy;
	}

	public void setAuditDate(Instant auditDate) {
		this.auditDate = auditDate;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
